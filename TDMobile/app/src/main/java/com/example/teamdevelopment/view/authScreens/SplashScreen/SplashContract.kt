package com.example.teamdevelopment.view.authScreens.SplashScreen

import com.example.teamdevelopment.view.base.ViewEvent
import com.example.teamdevelopment.view.base.ViewSideEffect
import com.example.teamdevelopment.view.base.ViewState

class SplashContract {

    sealed class Event : ViewEvent {
        object GetToken : Event()
        object OnTokenLoadedSuccess : Event()
        object OnTokenLoadedFailed : Event()
    }

    data class State(
        val isError: Boolean,
        val isNetworkError: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            object ToIntroducingScreen : Navigation()
        }
    }
}