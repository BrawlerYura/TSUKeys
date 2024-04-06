package com.example.teamdevelopment.domain.useCases.user

import com.example.teamdevelopment.domain.entities.RequestBodies.UserLoginRequestBody
import com.example.teamdevelopment.domain.entities.RequestBodies.UserProfileRequestBody
import com.example.teamdevelopment.domain.entities.data.TokenModel
import com.example.teamdevelopment.network.repository.UserRepository

class UserPutProfileUseCase(private val repository: UserRepository) {
    suspend fun invoke(body: UserProfileRequestBody): Result<Unit> {
        return try {
            Result.success(repository.putProfile(body))
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}