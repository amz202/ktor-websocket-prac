package com.example.websocket

import com.example.data.model.ChatMessage
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.websocket.Frame
import kotlinx.serialization.json.Json
import java.util.Collections

object ChatSessionManager {
    private val groupConnections = mutableMapOf<String, MutableSet<DefaultWebSocketServerSession>>()

    fun joinGroup(groupId: String, session: DefaultWebSocketServerSession) {
        synchronized(groupConnections) {
            val group = groupConnections.getOrPut(groupId) { mutableSetOf() }
            group.add(session)
        }
    }

    fun leaveGroup(groupId: String, session: DefaultWebSocketServerSession) {
        synchronized(groupConnections) {
            groupConnections[groupId]?.remove(session)
            if (groupConnections[groupId]?.isEmpty() == true) {
                groupConnections.remove(groupId)
            }
        }
    }

    suspend fun broadcastToGroup(groupId: String, message: ChatMessage) {
        val json = Json.encodeToString(message)
        val sessions = synchronized(groupConnections) {
            groupConnections[groupId]?.toSet() ?: emptySet()
        }

        sessions.forEach { session ->
            session.send(Frame.Text(json))
        }
    }

    fun unregister(session: DefaultWebSocketServerSession) {
        removeSessionFromAllGroups(session)
    }

    private fun removeSessionFromAllGroups(session: DefaultWebSocketServerSession) {
        val emptyGroups = mutableListOf<String>()
        synchronized(groupConnections) {
            groupConnections.forEach { (groupId, sessions) ->
                sessions.remove(session)
                if (sessions.isEmpty()) {
                    emptyGroups.add(groupId)
                }
            }
            emptyGroups.forEach { groupConnections.remove(it) }
        }
    }
}
