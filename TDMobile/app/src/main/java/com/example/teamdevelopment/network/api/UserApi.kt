package com.example.teamdevelopment.network.api

import com.example.teamdevelopment.domain.entities.RequestBodies.*
import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.data.TokenModel
import com.example.teamdevelopment.domain.entities.data.UserProfile
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApi {
    @POST("/api/user/register")
    suspend fun register(@Body body: UserRegisterRequestBody): TokenModel

    @POST("/api/user/login")
    suspend fun login(@Body body: UserLoginRequestBody): TokenModel

    @POST("/api/user/logout")
    suspend fun logout()

    @POST("/api/user/createChangeRole")
    suspend fun createChangeRole(
        @Query("desiredRole") desiredRole: String,
        @Query("deanId") deanId: String
    )

    @POST("/api/user/createQR")
    suspend fun createQR(
        @Query("keyId") keyId: String,
        @Query("pass") pass: String,
    )

    @GET("/api/user/getRole")
    suspend fun getRole()

    @GET("/api/user/profile")
    suspend fun profile(): ProfileModel

    @PUT("/api/user/profile")
    suspend fun profile(@Body body: UserProfileRequestBody)

    @PUT("/api/user/readQR")
    suspend fun readQR(
        @Query("pass") pass: String
    )

    @DELETE("/api/user/deleteQR")
    suspend fun deleteQR(@Body body: UserDeleteQRRequestBody)
}