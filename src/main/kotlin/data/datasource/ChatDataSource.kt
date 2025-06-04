package com.example.data.datasource

import com.example.data.model.ChatMessage
import com.example.data.model.EditMessageRequest
import com.example.data.model.SentMessage

interface ChatDataSource {
    suspend fun saveMessage(message: ChatMessage): Boolean
    suspend fun getRecentMessages(): List<ChatMessage>
    suspend fun deleteMessage(id: String): Boolean
    suspend fun editMessage(editMessageRequest: EditMessageRequest): Boolean
}