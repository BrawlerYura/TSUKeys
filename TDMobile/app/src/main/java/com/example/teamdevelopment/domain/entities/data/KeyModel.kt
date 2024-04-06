package com.example.teamdevelopment.domain.entities.data

import com.example.teamdevelopment.domain.entities.RequestBodies.UserProfileRequestBody
import com.example.teamdevelopment.domain.entities.enums.KeyStatusEnum
import com.example.teamdevelopment.network.repository.UserRepository

data class KeyModel (
    val id: String,
    val number: Int,
    val building: Int,
    val currentUserId: String?,
    val currentUser: UserProfile?,
    val deanId: String,
)