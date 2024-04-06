package com.example.teamdevelopment.network

import android.app.Application
import com.example.teamdevelopment.MyApplication
import com.example.teamdevelopment.network.api.AppApi
import com.example.teamdevelopment.network.api.DeanApi
import com.example.teamdevelopment.network.api.KeyApi
import com.example.teamdevelopment.network.api.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object Network {
    private const val BASE_URL = "http://92.125.32.68:5284"
    private val tokenManager = TokenManager()

    private fun getHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(20, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
            addInterceptor(MyInterceptor())
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
        }
        return client.build()
    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(getHttpClient())
            .build()
    }

    private val retrofit: Retrofit = getRetrofit()

    fun getTokenManager(): TokenManager {
        return this.tokenManager
    }

    var userId: String = ""

    fun getUserApi(): UserApi = retrofit.create(UserApi::class.java)

    fun getDeanApi(): DeanApi = retrofit.create(DeanApi::class.java)

    fun getKeyApi(): KeyApi = retrofit.create(KeyApi::class.java)

    fun getAppApi(): AppApi = retrofit.create(AppApi::class.java)
}