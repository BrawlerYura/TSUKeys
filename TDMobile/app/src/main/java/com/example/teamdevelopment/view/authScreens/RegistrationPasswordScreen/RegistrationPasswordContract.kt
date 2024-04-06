package com.example.teamdevelopment.view.authScreens.RegistrationPasswordScreen

import androidx.compose.ui.hapticfeedback.HapticFeedback
import com.example.teamdevelopment.domain.entities.RequestBodies.RegisterRequestBody
import com.example.teamdevelopment.view.base.ViewEvent
import com.example.teamdevelopment.view.base.ViewSideEffect
import com.example.teamdevelopment.view.base.ViewState

class RegistrationPasswordContract {

    sealed class Event : ViewEvent {
        class SavePasswordEvent(val password: String) : Event()
        class SaveRepeatedPasswordEvent(val repPassword: String) : Event()
        class SignUp(val haptic: HapticFeedback, val body: RegisterRequestBody) : Event()
        object NavigationToLogin : Event()
        object NavigationBack : Event()
    }

    data class State(
        val password: String,
        val repPassword: String,
        val isSuccess: Boolean?,
        val isPasswordValid: Boolean?,
        val isRepPasswordValid: Boolean?,
        val errorMessage: String?,
        val isBodyLoaded: Boolean,
        val isLoading: Boolean,
        val isError: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            object ToLogin : Navigation()
            object Back : Navigation()
        }
    }
}