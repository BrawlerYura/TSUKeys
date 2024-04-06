package com.example.teamdevelopment.domain.entities.RequestBodies

data class RegisterRequestBody(
    val name: String,
    val surname: String,
    val patronymic: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val gender: Int
)
