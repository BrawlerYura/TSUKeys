package com.example.teamdevelopment.di

import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.RegistrationPasswordViewModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.RegistrationViewModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen.SplashScreenViewModel
import com.example.teamdevelopment.domain.useCases.FormatDateUseCase
import com.example.teamdevelopment.view.MainScreens.MainScreen.MainViewModel
import com.example.teamdevelopment.view.MainScreens.MyKeysScreen.MyKeysScreenViewModel
import com.example.teamdevelopment.view.MainScreens.ProfileScreen.ProfileScreenViewModel
import com.example.teamdevelopment.view.MainScreens.selectKeyScreen.SelectKeyViewModel
import com.example.teamdevelopment.view.authScreens.LoginScreen.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { SplashScreenViewModel() }
    viewModel { RegistrationViewModel(validationUseCase = get(), formatDateUseCase = get()) }
    viewModel { RegistrationPasswordViewModel(validationUseCase = get()) }
    viewModel { LoginViewModel(validationUseCase = get()) }
    viewModel { MainViewModel() }
    viewModel { MyKeysScreenViewModel(formatDateUseCase = get()) }
    viewModel { SelectKeyViewModel() }
    viewModel {
        ProfileScreenViewModel(
            formatDateUseCase = get(),
            validationUseCase = get(),
            androidContext()
        )
    }
}