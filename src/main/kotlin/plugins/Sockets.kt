package com.example.plugins

import com.example.data.datasource.ChatDataSource
import com.example.websocket.startChatRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlin.time.Duration.Companion.seconds

fun Application.configureSockets(chatDataSource: ChatDataSource) {
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        startChatRoutes(chatDataSource)
    }
}
