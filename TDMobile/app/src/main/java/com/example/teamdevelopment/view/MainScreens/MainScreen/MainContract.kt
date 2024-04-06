package com.example.teamdevelopment.view.MainScreens.MainScreen

import com.example.teamdevelopment.domain.entities.data.LessonCard
import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.enums.RoleEnum
import com.example.teamdevelopment.domain.entities.enums.StatusEnum
import com.example.teamdevelopment.view.authScreens.LoginScreen.LoginContract
import com.example.teamdevelopment.view.authScreens.RegistrationPasswordScreen.RegistrationPasswordContract
import com.example.teamdevelopment.view.base.ViewEvent
import com.example.teamdevelopment.view.base.ViewSideEffect
import com.example.teamdevelopment.view.base.ViewState
import java.time.LocalDate

class MainContract {
    sealed class Event : ViewEvent {
        class SaveDateEvent(val date: LocalDate) : Event()
        class SaveCoupleEvent(val index: Int) : Event()
        class NavigateToProfile() : Event()
        class NavigateToSelectKey() : Event()
        class NavigateToMyKeys() : Event()
        class LoadProfile() : Event()
    }

    data class State(
        val isLoading: Boolean,
        val isError: Boolean,
        val lessonCards: List<LessonCard>,
        val selectedDate: LocalDate,
        val selectedCouple: Int,
        val profile: ProfileModel?
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToProfile : Navigation()
            class ToSelectKey(val date: LocalDate, val couple: Int) : Navigation()
            class ToMyKeys() : Navigation()
        }
    }
}