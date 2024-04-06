package com.example.teamdevelopment.domain.useCases.user

import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.data.UserProfile
import com.example.teamdevelopment.network.repository.UserRepository

class UserChangeRoleUseCase(private val repository: UserRepository) {

    suspend fun invoke(desiredRole: String, deanId: String): Result<Unit> {
        return try {
            Result.success(repository.createChangeRole(desiredRole = desiredRole, deanId = deanId))
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}