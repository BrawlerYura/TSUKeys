package com.example.teamdevelopment.view.authScreens.IntroducingScreen

import com.example.teamdevelopment.view.base.ViewSideEffect

class IntroducingContract {
    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToLogin : Navigation()
            object ToRegistration : Navigation()
        }
    }
}