package com.example.routes

import com.example.data.datasource.GroupDataSource
import com.example.data.model.Group
import com.example.data.model.request.GroupRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.createGroup(groupDataSource: GroupDataSource){
    post("/createGroup") {
        val groupRequest = try {
            call.receive<GroupRequest>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid request body")
            return@post
        }
        val group = Group(
            name = groupRequest.name,
            tag = groupRequest.tag,
        )
        val created = groupDataSource.createGroup(group)
        if (created) {
            call.respond(HttpStatusCode.Created, "Group created successfully.")
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Failed to create group.")
        }
    }
}

fun Route.getGroups(groupDataSource: GroupDataSource) {
    get("/getGroups") {
        val groups = groupDataSource.getGroups()
        if (groups.isNotEmpty()) {
            call.respond(HttpStatusCode.OK, groups)
        } else {
            call.respond(HttpStatusCode.NoContent, "No groups found.")
        }
    }
}

fun Route.deleteGroup(groupDataSource: GroupDataSource) {
    delete("/{id}/deleteGroup") {
        val id = call.parameters["id"]
        if (id != null) {
            val deleted = groupDataSource.deleteGroup(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Group deleted successfully.")
            } else {
                call.respond(HttpStatusCode.NotFound, "Group not found.")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid group ID.")
        }
    }
}