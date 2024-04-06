package com.example.teamdevelopment.domain.useCases.app

import com.example.teamdevelopment.domain.entities.data.RequestModel
import com.example.teamdevelopment.network.repository.AppRepository

class AppAllAppsUseCase(private val repository: AppRepository) {
    suspend fun invoke(): Result<List<RequestModel>> {
        return try {
            Result.success(repository.allApps())
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}