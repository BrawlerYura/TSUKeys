package com.example.teamdevelopment.domain.useCases.user

import com.example.teamdevelopment.domain.entities.RequestBodies.UserRegisterRequestBody
import com.example.teamdevelopment.domain.entities.data.TokenModel
import com.example.teamdevelopment.network.repository.UserRepository

class UserRegisterUseCase(private val repository: UserRepository) {
    suspend fun invoke(body: UserRegisterRequestBody): Result<TokenModel> {
        return try {
            Result.success(repository.register(body))
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}