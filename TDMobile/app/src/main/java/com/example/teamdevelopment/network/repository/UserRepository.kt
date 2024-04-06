package com.example.teamdevelopment.network.repository

import com.example.teamdevelopment.domain.entities.RequestBodies.*
import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.data.TokenModel
import com.example.teamdevelopment.domain.entities.data.UserProfile
import com.example.teamdevelopment.network.api.UserApi

class UserRepository(private val api: UserApi) {
    suspend fun register(body: UserRegisterRequestBody): TokenModel = api.register(body)

    suspend fun login(body: UserLoginRequestBody): TokenModel = api.login(body)

    suspend fun logout() = api.logout()

    suspend fun createChangeRole(desiredRole: String, deanId: String) = api.createChangeRole(desiredRole, deanId)

    suspend fun createQR(keyId: String, pass: String) = api.createQR(keyId, pass)

    suspend fun getRole() = api.getRole()

    suspend fun profile(): ProfileModel = api.profile()

    suspend fun putProfile(body: UserProfileRequestBody) = api.profile(body)

    suspend fun readQR(pass: String) = api.readQR(pass)

    suspend fun deleteQR(body: UserDeleteQRRequestBody) = api.deleteQR(body)
}