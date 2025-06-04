package com.example.websocket

import com.example.data.datasource.ChatDataSource
import com.example.data.model.ChatMessage
import com.example.data.model.SentMessage
import io.ktor.server.routing.Route
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

fun Route.startChatRoutes(chatDataSource: ChatDataSource){
    webSocket("/chat") {
        ChatSessionManager.register(this)
        try{
            for (frame in incoming){
                if (frame is Frame.Text) {
                    val incoming = Json.decodeFromString<SentMessage>(frame.readText())
                    val timeStamp = LocalDateTime.now().toString()
                    val chatMessage = ChatMessage(sender = incoming.sender, message = incoming.message, timeStamp = timeStamp)

                    chatDataSource.saveMessage(chatMessage)
                    ChatSessionManager.broadcast(chatMessage)
                }
            }
        }catch (e: Exception) {
            e.printStackTrace()
        } finally {
            ChatSessionManager.unregister(this)
        }
    }
}