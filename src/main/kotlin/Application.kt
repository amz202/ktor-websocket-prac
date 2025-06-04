package com.example

import com.example.data.datasource.ChatDataSource
import com.example.data.datasource.MongoChatDataSource
import com.example.plugins.configureMonitoring
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.plugins.configureSockets
import io.ktor.server.application.*
import com.mongodb.kotlin.client.coroutine.MongoClient

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val mongoPw = System.getenv("MONGO_PW")

    val dbName = "ChatDatabase"
    val db = MongoClient.create(
        connectionString = "mongodb+srv://abdulmajidzeeshan4:${mongoPw}@cluster0.blwr6uy.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    ).getDatabase(dbName)
    val chatDataSource = MongoChatDataSource(db)

    configureSerialization()
    configureMonitoring()
    configureSockets(chatDataSource)
    configureRouting(chatDataSource)
}
