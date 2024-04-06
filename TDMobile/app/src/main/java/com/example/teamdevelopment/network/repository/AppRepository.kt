package com.example.teamdevelopment.network.repository

import com.example.teamdevelopment.domain.entities.RequestBodies.*
import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.data.RequestModel
import com.example.teamdevelopment.domain.entities.data.TokenModel
import com.example.teamdevelopment.domain.entities.data.UserProfile
import com.example.teamdevelopment.network.api.AppApi
import com.example.teamdevelopment.network.api.KeyApi
import com.example.teamdevelopment.network.api.UserApi

class AppRepository(private val api: AppApi) {
    suspend fun requestKey(keyId: String, schedule: String, date: String, isRepeat: Boolean): String = api.create(keyId, schedule, date, isRepeat)
    suspend fun allApps(): List<RequestModel> = api.allApps()
}