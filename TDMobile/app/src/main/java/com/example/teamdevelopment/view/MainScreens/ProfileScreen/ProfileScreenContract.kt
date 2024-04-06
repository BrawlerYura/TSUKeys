package com.example.teamdevelopment.view.MainScreens.ProfileScreen

import androidx.compose.ui.hapticfeedback.HapticFeedback
import com.example.teamdevelopment.domain.entities.data.NewProfileModel
import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.data.UserProfile
import com.example.teamdevelopment.domain.entities.enums.RoleEnum
import com.example.teamdevelopment.domain.entities.enums.StatusEnum
import com.example.teamdevelopment.view.MainScreens.MainScreen.MainContract
import com.example.teamdevelopment.view.base.ViewEvent
import com.example.teamdevelopment.view.base.ViewSideEffect
import com.example.teamdevelopment.view.base.ViewState

class ProfileScreenContract {

    sealed class Event : ViewEvent {
        class SaveNameEvent(val name: String) : Event()
        class SaveGenderEvent(val gender: Int) : Event()
        class SaveEmailEvent(val email: String) : Event()
        class SaveBirthDateEvent(val birthDate: String) : Event()
        class SaveBirthDateWithFormatEvent(val birthDate: Long?) : Event()
        class SavePatronymicEvent(val patronymic: String) : Event()
        class SaveSurnameEvent(val surname: String) : Event()
        class PutNewUserDetails(val haptic: HapticFeedback) : Event()
        object RefreshScreen : Event()
        object LoadUserDetails : Event()
        object Logout : Event()
        object Back : Event()
        object NavigationToFavorite : Event()
        object NavigationToIntroducing : Event()
    }

    data class State(
        val id: String,
        val email: String,
        val name: String,
        val surname: String,
        val patronymic: String,
        val gender: Int,
        val birthDate: String,
        val isLoaded: Boolean,
        val isError: Boolean,
        val isSuccess: Boolean,
        val errorMessage: String?,
        val profileModel: ProfileModel?,
        val password: String,
        val isEnable: Boolean,
        val isCancelEnable: Boolean,
        val isNameValid: Boolean,
        val isSurnameValid: Boolean,
        val isEmailValid: Boolean,
        val isBirthDateValid: Boolean,
        val isRefreshing: Boolean,
        val profileStatus: StatusEnum,
        val isActive: Boolean,
        val isDeanWorker: Boolean,
        val role: RoleEnum,
        val isDenied: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object Back : Navigation()
            object ToIntroducing : Navigation()
        }
    }
}
