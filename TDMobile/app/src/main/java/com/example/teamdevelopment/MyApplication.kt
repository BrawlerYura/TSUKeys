package com.example.teamdevelopment

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.teamdevelopment.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
//    companion object {
//        @SuppressLint("StaticFieldLeak")
//        lateinit var context: Context
//    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(AppModules)
        }
    }

    companion object {
        lateinit var context: Context
            private set
    }
}