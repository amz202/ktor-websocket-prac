package com.example.websocket

import com.example.data.model.ChatMessage
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.websocket.Frame
import kotlinx.serialization.json.Json
import java.util.Collections

object ChatSessionManager {
    private val connections = Collections.synchronizedSet(mutableSetOf<DefaultWebSocketServerSession>())

    fun register(session: DefaultWebSocketServerSession) {
        connections.add(session)
    }

    fun unregister(session: DefaultWebSocketServerSession) {
        connections.remove(session)
    }

    suspend fun broadcast(message: ChatMessage){
        val json = Json.encodeToString(message)
        connections.forEach {
            try {
                it.send(Frame.Text(json))
            } catch (_: Exception) {}
        }
    }
}