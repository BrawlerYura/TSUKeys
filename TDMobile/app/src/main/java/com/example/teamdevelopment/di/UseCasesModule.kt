package com.example.teamdevelopment.di

import com.example.teamdevelopment.domain.useCases.FormatDateUseCase
import com.example.teamdevelopment.domain.useCases.ValidationUseCase
import com.example.teamdevelopment.domain.useCases.user.UserGetProfileUseCase
import org.koin.dsl.module

val UseCasesModule = module {
    single { FormatDateUseCase() }
    single { ValidationUseCase() }
}