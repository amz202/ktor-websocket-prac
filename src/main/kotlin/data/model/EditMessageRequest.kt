package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EditMessageRequest(
    val id: String,
    val newMessage: String
)