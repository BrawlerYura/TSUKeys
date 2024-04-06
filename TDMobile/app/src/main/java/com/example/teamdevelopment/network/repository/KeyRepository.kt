package com.example.teamdevelopment.network.repository

import com.example.teamdevelopment.domain.entities.RequestBodies.*
import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.data.TokenModel
import com.example.teamdevelopment.domain.entities.data.UserProfile
import com.example.teamdevelopment.network.api.KeyApi
import com.example.teamdevelopment.network.api.UserApi

class KeyRepository(private val api: KeyApi) {
    suspend fun getKeysList(date: String, cell: String): List<KeyModel> = api.listKeys(date, cell)

    suspend fun myKeys(): List<KeyModel> = api.myKeys()
    suspend fun returnKey(id: String) = api.returnKey(id)
}