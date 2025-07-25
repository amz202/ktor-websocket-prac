package com.example.routes

import com.example.data.datasource.ChatDataSource
import com.example.data.model.ChatMessage
import com.example.data.model.EditMessageRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.recentChat(chatDataSource: ChatDataSource){
    get("/{groupId}/recentChat"){
        val groupId = call.parameters["groupId"]
        if (groupId == null) {
            call.respond(HttpStatusCode.BadRequest, "Group ID is required.")
            return@get
        }
        val messages = chatDataSource.getRecentMessages(groupId)
        if (messages.isNotEmpty()) {
            call.respond(HttpStatusCode.OK, messages)
        } else {
            call.respond(HttpStatusCode.NoContent, "No recent messages found for this group.")
        }
    }
}

fun Route.deleteMessage(chatDataSource: ChatDataSource){
    delete("/{id}/delete"){
        val id = call.parameters["id"]
        if( id == null ){
            call.respond(HttpStatusCode.BadRequest, "Invalid message ID.")
            return@delete
        }
        val deleted = chatDataSource.deleteMessage(id)
        if (deleted) {
            call.respond(HttpStatusCode.OK, "Message deleted successfully.")
        } else {
            call.respond(HttpStatusCode.NotFound, "Message not found.")
        }
    }
}

fun Route.editMessage(chatDataSource: ChatDataSource){
    get("/edit"){
        val body = call.receive<EditMessageRequest>()
        val edited = chatDataSource.editMessage(body)
        if (edited) {
            call.respond(HttpStatusCode.OK, "Message edited successfully.")
        } else {
            call.respond(HttpStatusCode.NotFound, "Message not found.")
        }
    }
}