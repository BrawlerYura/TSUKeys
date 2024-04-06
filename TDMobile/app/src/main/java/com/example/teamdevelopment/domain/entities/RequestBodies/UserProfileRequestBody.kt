package com.example.teamdevelopment.domain.entities.RequestBodies

data class UserProfileRequestBody(
    val name: String,
    val email: String,
    val password: String
)
