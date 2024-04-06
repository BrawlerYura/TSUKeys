package com.example.teamdevelopment.network.api

import com.example.teamdevelopment.domain.entities.RequestBodies.AppConfirmRequestBody
import com.example.teamdevelopment.domain.entities.RequestBodies.AppCreateRequestBody
import com.example.teamdevelopment.domain.entities.RequestBodies.AppDeleteRequestBody
import com.example.teamdevelopment.domain.entities.RequestBodies.AppShowKeyRequestBody
import com.example.teamdevelopment.domain.entities.data.RequestModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AppApi {
    @POST("/api/app/create")
    suspend fun create(
        @Query("keyId") keyId: String,
        @Query("schedule") schedule: String,
        @Query("date") date: String,
        @Query("isRepeat") isRepeat: Boolean
    ) : String

    @GET("/api/app/show")
    suspend fun show()

    @GET("/api/app/key/show")
    suspend fun showKey(@Body body: AppShowKeyRequestBody)

    @PUT("/api/app/confirm")
    suspend fun confirm(@Body body: AppConfirmRequestBody)

    @DELETE("/api/app/delete")
    suspend fun delete(@Body body: AppDeleteRequestBody)

    @GET("/api/app/allApps")
    suspend fun allApps(): List<RequestModel>
}