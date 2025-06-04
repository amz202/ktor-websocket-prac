package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SentMessage(
    val sender: String,
    val message: String,
)