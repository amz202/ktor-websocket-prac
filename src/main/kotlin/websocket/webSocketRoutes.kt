package com.example.websocket

import com.example.data.datasource.ChatDataSource
import com.example.data.model.ChatMessage
import com.example.data.model.Events.JoinGroup
import com.example.data.model.Events.LeaveGroup
import com.example.data.model.Events.SendMessage
import com.example.data.model.Events.WebSocketEvent
import io.ktor.server.routing.Route
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

fun Route.startChatRoutes(chatDataSource: ChatDataSource) {
    webSocket("/chat") {
        val json = Json {
            ignoreUnknownKeys = true
            classDiscriminator = "type"
        }

        try {
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val event = json.decodeFromString<WebSocketEvent>(frame.readText())

                    when (event) {
                        is JoinGroup -> ChatSessionManager.joinGroup(event.groupId, this)

                        is LeaveGroup -> ChatSessionManager.leaveGroup(event.groupId, this)

                        is SendMessage -> {
                            val message = ChatMessage(
                                groupId = event.groupId,
                                sender = event.sender,
                                message = event.message,
                                timeStamp = LocalDateTime.now().toString()
                            )
                            chatDataSource.saveMessage(message)
                            ChatSessionManager.broadcastToGroup(event.groupId, message)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            ChatSessionManager.unregister(this)
        }
    }
}
