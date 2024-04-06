package com.example.teamdevelopment.network.repository

import com.example.teamdevelopment.domain.entities.RequestBodies.*
import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.data.TokenModel
import com.example.teamdevelopment.domain.entities.data.UserProfile
import com.example.teamdevelopment.network.api.AppApi
import com.example.teamdevelopment.network.api.DeanApi
import com.example.teamdevelopment.network.api.KeyApi
import com.example.teamdevelopment.network.api.UserApi

class DeanRepository(private val api: DeanApi) {
    suspend fun getDeans(): List<DeanModel> = api.getDeans()
}