package com.example.plugins

import com.example.data.datasource.ChatDataSource
import com.example.data.datasource.GroupDataSource
import com.example.routes.createGroup
import com.example.routes.deleteGroup
import com.example.routes.deleteMessage
import com.example.routes.editMessage
import com.example.routes.getGroups
import com.example.routes.recentChat
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(chatDataSource: ChatDataSource, groupDataSource: GroupDataSource) {
    routing {
        recentChat(chatDataSource)
        deleteMessage(chatDataSource)
        editMessage(chatDataSource)
        createGroup(groupDataSource)
        getGroups(groupDataSource)
        deleteGroup(groupDataSource)
    }
}
