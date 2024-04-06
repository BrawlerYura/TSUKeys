package com.example.teamdevelopment.view.authScreens.RegistrationScreen


import com.example.teamdevelopment.domain.entities.RequestBodies.RegisterRequestBody
import com.example.teamdevelopment.view.base.ViewEvent
import com.example.teamdevelopment.view.base.ViewSideEffect
import com.example.teamdevelopment.view.base.ViewState

class RegistrationContract {

    sealed class Event : ViewEvent {
        class SaveNameEvent(val name: String) : Event()

        class SaveSurnameEvent(val surname: String) : Event()

        class SavePatronymicEvent(val patronymic: String) : Event()

        class SaveGenderEvent(val gender: Int) : Event()

        class SaveEmailEvent(val email: String) : Event()

        class SaveBirthDateEvent(val birthDate: String) : Event()

        class SaveBirthDateWithFormatEvent(val birthDate: Long?) : Event()
    }

    data class State(
        val name: String,
        val surname: String,
        val patronymic: String,
        val gender: Int,
        val email: String,
        val birthDate: String,
        val apiBirthDate: String,
        val isNameValid: Boolean?,
        val isSurnameValid: Boolean?,
        val isPatronymicValid: Boolean?,
        val isEmailValid: Boolean?,
        val isBirthDateValid: Boolean?
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            class NextScreen(val registerRequestBody: RegisterRequestBody) : Navigation()
            object ToLogin : Navigation()
            object Back : Navigation()
        }
    }
}