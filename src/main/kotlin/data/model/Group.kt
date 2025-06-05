package com.example.data.model

import com.example.plugins.ObjectIdSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

data class Group(
    @SerialName("id")
    @Serializable(with = ObjectIdSerializer::class)
    val id: ObjectId = ObjectId(),
    val name: String
)