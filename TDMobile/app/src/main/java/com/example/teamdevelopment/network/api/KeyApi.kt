package com.example.teamdevelopment.network.api

import com.example.teamdevelopment.domain.entities.data.KeyModel
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface KeyApi {
    @POST("/api/key/create")
    suspend fun create()

    @GET("/api/key/listKeys")
    suspend fun listKeys(
        @Query("date") date: String,
        @Query("cell") cell: String
    ) : List<KeyModel>

    @GET("/api/key/schedule")
    suspend fun schedule()

    @PUT("/api/key/handOver")
    suspend fun handOver()

    @PUT("/api/key/return")
    suspend fun returnKey(
        @Query("id") id: String,
    )

    @DELETE("/api/key/delete")
    suspend fun delete()

    @GET("/api/key/myKeys")
    suspend fun myKeys() : List<KeyModel>
}