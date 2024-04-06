package com.example.teamdevelopment.domain.useCases.dean

import com.example.teamdevelopment.domain.entities.RequestBodies.DeanModel
import com.example.teamdevelopment.network.repository.AppRepository
import com.example.teamdevelopment.network.repository.DeanRepository

class DeanGetDeans(private val repository: DeanRepository) {
    suspend fun invoke(): Result<List<DeanModel>> {
        return try {
            Result.success(repository.getDeans())
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}