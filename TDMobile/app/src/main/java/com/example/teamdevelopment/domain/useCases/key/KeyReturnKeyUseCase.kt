package com.example.teamdevelopment.domain.useCases.key

import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.network.repository.KeyRepository

class KeyReturnKeyUseCase(private val repository: KeyRepository) {
    suspend fun invoke(id: String): Result<Unit> {
        return try {
            Result.success(repository.returnKey(id))
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}