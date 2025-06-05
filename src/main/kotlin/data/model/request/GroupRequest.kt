package com.example.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class GroupRequest(
    val name:String,
    val tag:String
)