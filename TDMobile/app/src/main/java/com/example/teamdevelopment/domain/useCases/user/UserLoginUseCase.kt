package com.example.teamdevelopment.domain.useCases.user

import com.example.teamdevelopment.domain.entities.RequestBodies.UserLoginRequestBody
import com.example.teamdevelopment.domain.entities.RequestBodies.UserRegisterRequestBody
import com.example.teamdevelopment.domain.entities.data.TokenModel
import com.example.teamdevelopment.network.repository.UserRepository

class UserLoginUseCase(private val repository: UserRepository) {
    suspend fun invoke(body: UserLoginRequestBody): Result<TokenModel> {
        return try {
            Result.success(repository.login(body))
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}