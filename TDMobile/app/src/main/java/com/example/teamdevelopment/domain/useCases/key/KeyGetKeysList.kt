package com.example.teamdevelopment.domain.useCases.key

import com.example.teamdevelopment.domain.entities.RequestBodies.UserLoginRequestBody
import com.example.teamdevelopment.domain.entities.RequestBodies.UserRegisterRequestBody
import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.domain.entities.data.TokenModel
import com.example.teamdevelopment.network.repository.KeyRepository
import com.example.teamdevelopment.network.repository.UserRepository

class KeyGetKeysList(private val repository: KeyRepository) {
    suspend fun invoke(date: String, cell: String): Result<List<KeyModel>> {
        return try {
            Result.success(repository.getKeysList(date = date, cell = cell))
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}