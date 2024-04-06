package com.example.teamdevelopment.domain.useCases.user

import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.data.UserProfile
import com.example.teamdevelopment.network.repository.UserRepository

class UserGetProfileUseCase(private val repository: UserRepository) {

    suspend fun invoke(): Result<ProfileModel> {
        return try {
            Result.success(repository.profile())
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}