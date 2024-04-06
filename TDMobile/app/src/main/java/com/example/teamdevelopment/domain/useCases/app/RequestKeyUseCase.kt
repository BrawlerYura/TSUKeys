package com.example.teamdevelopment.domain.useCases.app

import com.example.teamdevelopment.network.repository.AppRepository

class RequestKeyUseCase(private val repository: AppRepository) {
    suspend fun invoke(keyId: String, schedule: String, date: String, isRepeat: Boolean): Result<String> {
        return try {
            Result.success(repository.requestKey(keyId, schedule, date, isRepeat))
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}