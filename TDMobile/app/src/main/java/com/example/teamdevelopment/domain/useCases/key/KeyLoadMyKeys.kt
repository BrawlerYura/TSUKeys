package com.example.teamdevelopment.domain.useCases.key

import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.network.repository.KeyRepository

class KeyLoadMyKeys(private val repository: KeyRepository) {
    suspend fun invoke(): Result<List<KeyModel>> {
        return try {
            Result.success(repository.myKeys())
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}