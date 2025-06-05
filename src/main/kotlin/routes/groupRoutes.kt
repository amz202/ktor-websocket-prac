package com.example.routes

import com.example.data.datasource.GroupDataSource
import com.example.data.model.Group
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.createGroup(groupDataSource: GroupDataSource){
    post("/createGroup") {
        val name = call.receive<Group>()
        val created = groupDataSource.createGroup(name)
        if (created) {
            call.respond(HttpStatusCode.Created, "Group created successfully.")
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Failed to create group.")
        }
    }
}

fun Route.getGroups(groupDataSource: GroupDataSource) {
    post("/getGroups") {
        val groups = groupDataSource.getGroups()
        if (groups.isNotEmpty()) {
            call.respond(HttpStatusCode.OK, groups)
        } else {
            call.respond(HttpStatusCode.NoContent, "No groups found.")
        }
    }
}

fun Route.deleteGroup(groupDataSource: GroupDataSource) {
    post("/deleteGroup/{id}") {
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