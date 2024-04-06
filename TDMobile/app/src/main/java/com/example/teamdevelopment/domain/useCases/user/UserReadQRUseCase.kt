package com.example.teamdevelopment.domain.useCases.user

import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.network.repository.UserRepository

class UserReadQRUseCase(private val repository: UserRepository) {
    suspend fun invoke(pass: String): Result<Unit> {
        return try {
            Result.success(repository.readQR(pass))
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}