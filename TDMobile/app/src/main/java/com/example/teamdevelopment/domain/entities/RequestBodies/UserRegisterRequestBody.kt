package com.example.teamdevelopment.domain.entities.RequestBodies

data class UserRegisterRequestBody(
    val name: String,
    val email: String,
    val password: String,
    val birthDate: String
)