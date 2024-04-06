package com.example.teamdevelopment.domain.entities.data

import com.example.teamdevelopment.domain.entities.enums.RoleEnum
import com.example.teamdevelopment.domain.entities.enums.StatusEnum

data class ProfileModel (
    val email: String,
    val name: String,
    val birthDate: String,
    val isActive: Boolean,
    val isDeanWorker: Boolean?,
    val role: RoleEnum,
    val isDenied: Boolean
)

data class UserProfile (
//    val id: String,
    val email: String,
    val name: String,
//    val surname: String,
//    val patronymic: String? = null,
//    val birthDate: String,
//    val gender: Int,
//    val profileStatus: StatusEnum,
//    val role: RoleEnum
)

data class NewProfileModel (
    val id: String,
    val email: String,
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val birthDate: String,
    val gender: Int,
    val profileStatus: StatusEnum,
    val role: RoleEnum
)