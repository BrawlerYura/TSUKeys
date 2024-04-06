package com.example.teamdevelopment.network.api

import com.example.teamdevelopment.domain.entities.RequestBodies.DeanModel
import retrofit2.http.GET
import retrofit2.http.POST

interface DeanApi {
    @POST("/api/dean/register")
    suspend fun register()

    @POST("/api/dean/login")
    suspend fun login()

    @POST("/api/dean/logout")
    suspend fun logout()

    @POST("/api/dean/acceptChangeRole")
    suspend fun acceptChangeRole()

    @POST("/api/dean/AssignDeanWorker")
    suspend fun assignDeanWorker()

    @GET("/api/dean/profile")
    suspend fun profile()

    @GET("/api/dean/getListChangeRole")
    suspend fun getListChangeRole()

    @GET("/api/dean/allKeys")
    suspend fun allKeys()

    @GET("/api/dean/allFreeKeys")
    suspend fun allFreeKeys()

    @GET("/api/dean/GetDeans")
    suspend fun getDeans(): List<DeanModel>

    @GET("/api/dean/GetAssignedUsers")
    suspend fun getAssignedUsers()

    @GET("/api/dean/GetDeanWorkers")
    suspend fun getDeanWorkers()
}