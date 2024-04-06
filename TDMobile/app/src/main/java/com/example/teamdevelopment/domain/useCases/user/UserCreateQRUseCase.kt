package com.example.teamdevelopment.domain.useCases.user

import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.network.repository.UserRepository

class UserCreateQRUseCase(private val repository: UserRepository) {
    suspend fun invoke(keyId: String, pass: String): Result<Unit> {
        return try {
            Result.success(repository.createQR(keyId, pass))
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}