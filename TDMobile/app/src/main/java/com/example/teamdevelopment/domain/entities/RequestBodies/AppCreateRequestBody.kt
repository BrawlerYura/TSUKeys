package com.example.teamdevelopment.domain.entities.RequestBodies

data class AppCreateRequestBody(
    val keyId: String,
    val schedule: String,
    val date: String,
    val isRepeat: Boolean
)
