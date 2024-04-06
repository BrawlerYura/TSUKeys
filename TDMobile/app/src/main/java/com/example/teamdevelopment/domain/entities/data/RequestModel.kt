package com.example.teamdevelopment.domain.entities.data

import com.example.teamdevelopment.domain.entities.RequestBodies.UserProfileRequestBody
import com.example.teamdevelopment.domain.entities.enums.KeyStatusEnum
import com.example.teamdevelopment.network.repository.UserRepository

data class RequestModel (
    val id: String,
    val key: KeyModel,
    val schedule: String,
    var date: String,
    val isRepeat: Boolean,
)