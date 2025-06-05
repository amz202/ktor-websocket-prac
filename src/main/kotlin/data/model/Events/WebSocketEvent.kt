package com.example.data.model.Events
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("WebSocketEvent")
sealed class WebSocketEvent {
    abstract val groupId: String
}

@Serializable
@SerialName("JoinGroup")
data class JoinGroup(
    override val groupId: String
) : WebSocketEvent()

@Serializable
@SerialName("LeaveGroup")
data class LeaveGroup(
    override val groupId: String
) : WebSocketEvent()

@Serializable
@SerialName("SendMessage")
data class SendMessage(
    override val groupId: String,
    val sender: String,
    val message: String
) : WebSocketEvent()
