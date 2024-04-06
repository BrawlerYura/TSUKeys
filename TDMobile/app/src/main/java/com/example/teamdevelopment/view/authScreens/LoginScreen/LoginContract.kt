package com.example.teamdevelopment.view.authScreens.LoginScreen

import androidx.compose.ui.hapticfeedback.HapticFeedback
import com.example.teamdevelopment.view.base.ViewEvent
import com.example.teamdevelopment.view.base.ViewSideEffect
import com.example.teamdevelopment.view.base.ViewState

class LoginContract {

    sealed class Event : ViewEvent {
        class SaveLoginEvent(val login: String) : Event()
        class SavePasswordEvent(val password: String) : Event()
        class SignIn(val haptic: HapticFeedback) : Event()
        object NavigationToRegistration : Event()
        object NavigationBack : Event()
    }

    data class State(
        val email: String,
        val password: String,
        val isSuccess: Boolean?,
        val buttonEnabled: Boolean,
        val errorMessage: String?,
        val isLoading: Boolean,
        val isError: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            object ToRegistration : Navigation()
            object Back : Navigation()
        }
    }
}