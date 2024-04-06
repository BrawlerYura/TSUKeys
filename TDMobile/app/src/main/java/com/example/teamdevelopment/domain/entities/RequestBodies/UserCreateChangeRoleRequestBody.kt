package com.example.teamdevelopment.domain.entities.RequestBodies

data class UserCreateChangeRoleRequestBody(
    val desiredRole: String,
    val deanId: String
)
