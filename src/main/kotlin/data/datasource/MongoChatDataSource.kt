package com.example.data.datasource

import com.example.data.model.ChatMessage
import com.example.data.model.EditMessageRequest
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates

class MongoChatDataSource(db: MongoDatabase) : ChatDataSource {
    private val chats = db.getCollection<ChatMessage>("chatMessages")

    override suspend fun saveMessage(message: ChatMessage): Boolean {
        return chats.insertOne(message).wasAcknowledged()
    }

    override suspend fun getRecentMessages(): List<ChatMessage> {
        return chats.find().toList()
    }

    override suspend fun deleteMessage(id: String): Boolean {
        val deleteResult =  chats.deleteOne(eq("id",id))
        return deleteResult.deletedCount>0
    }

    override suspend fun editMessage(editMessageRequest: EditMessageRequest): Boolean {
        val updateResult = chats.updateOne(
            eq("id", editMessageRequest.id),
            Updates.set("message", editMessageRequest.newMessage)
        )
        return updateResult.modifiedCount > 0
    }
}