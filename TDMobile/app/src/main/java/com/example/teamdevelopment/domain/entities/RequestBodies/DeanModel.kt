package com.example.teamdevelopment.domain.entities.RequestBodies

data class DeanModel(
    val facultyNumber: String,
    val id: String,
    val email: String,
    val password: String,
    val isActive: Boolean,
    val isDenied: Boolean,
    val name: String
)
