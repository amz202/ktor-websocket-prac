package com.example.plugins

import com.example.data.datasource.ChatDataSource
import com.example.routes.deleteMessage
import com.example.routes.editMessage
import com.example.routes.recentChat
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(chatDataSource: ChatDataSource) {
    routing {
        recentChat(chatDataSource)
        deleteMessage(chatDataSource)
        editMessage(chatDataSource)
    }
}
