package com.example.teamdevelopment.view.MainScreens.ProfileScreen

import android.content.Context
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.lifecycle.viewModelScope
import com.example.teamdevelopment.domain.entities.RequestBodies.UserProfileRequestBody
import com.example.teamdevelopment.domain.entities.data.NewProfileModel
import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.data.UserProfile
import com.example.teamdevelopment.domain.entities.enums.RoleEnum
import com.example.teamdevelopment.domain.entities.enums.StatusEnum
import com.example.teamdevelopment.domain.useCases.FormatDateUseCase
import com.example.teamdevelopment.domain.useCases.ValidationUseCase
import com.example.teamdevelopment.domain.useCases.user.UserGetProfileUseCase
import com.example.teamdevelopment.domain.useCases.user.UserLogoutUseCase
import com.example.teamdevelopment.domain.useCases.user.UserPutProfileUseCase
import com.example.teamdevelopment.network.Network
import com.example.teamdevelopment.network.repository.UserRepository
import com.example.teamdevelopment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val formatDateUseCase: FormatDateUseCase,
    private val validationUseCase: ValidationUseCase,
    private val context: Context,
) : BaseViewModel<ProfileScreenContract.Event, ProfileScreenContract.State, ProfileScreenContract.Effect>() {

    override fun setInitialState() = ProfileScreenContract.State(
        id = "",
        email = "",
        name = "",
        surname = "",
        patronymic = "",
        gender = 0,
        birthDate = "",
        isSuccess = true,
        isLoaded = true,
        isError = false,
        errorMessage = null,
        profileModel = null,
        password = "",
        isEnable = false,
        isCancelEnable = false,
        isNameValid = true,
        isSurnameValid = true,
        isEmailValid = true,
        isBirthDateValid = true,
        isRefreshing = true,
        isActive = false,
        isDeanWorker = false,
        profileStatus = StatusEnum.Unconfirmed,
        role = RoleEnum.Student,
        isDenied = true
    )

    override fun handleEvents(event: ProfileScreenContract.Event) {
        when (event) {
            is ProfileScreenContract.Event.RefreshScreen -> refreshScreen()
            is ProfileScreenContract.Event.SaveEmailEvent -> saveEmail(email = event.email)
            is ProfileScreenContract.Event.SavePatronymicEvent -> savePatronymic(patronymic = event.patronymic)
            is ProfileScreenContract.Event.SaveNameEvent -> saveName(name = event.name)
            is ProfileScreenContract.Event.SaveSurnameEvent -> saveSurname(surname = event.surname)
            is ProfileScreenContract.Event.SaveGenderEvent -> saveGender(gender = event.gender)
            is ProfileScreenContract.Event.SaveBirthDateEvent -> saveBirthDate(birthDate = event.birthDate)
            is ProfileScreenContract.Event.SaveBirthDateWithFormatEvent -> saveBirthDate(
                formatDateUseCase.formatDateToTextField(event.birthDate)
            )
            is ProfileScreenContract.Event.PutNewUserDetails -> putUserDetails(haptic = event.haptic)
            is ProfileScreenContract.Event.LoadUserDetails -> loadUserDetails()
            is ProfileScreenContract.Event.Logout -> logout()
            is ProfileScreenContract.Event.Back -> setEffect { ProfileScreenContract.Effect.Navigation.Back }
            is ProfileScreenContract.Event.NavigationToFavorite -> {  }
            is ProfileScreenContract.Event.NavigationToIntroducing -> {  }
        }
    }

    private fun refreshScreen() {
        setState {
            copy(
                id = "",
                email = "",
                name = "",
                surname = "",
                patronymic = "",
                gender = 0,
                birthDate = "",
                isLoaded = false,
                isError = false,
                errorMessage = null,
                profileModel = null,
                isEnable = false,
                isSuccess = true,
                isCancelEnable = false,
                isNameValid = true,
                isSurnameValid = true,
                isEmailValid = true,
                isBirthDateValid = true,
                isRefreshing = true,
                profileStatus = StatusEnum.Unconfirmed,
                role = RoleEnum.Student,
            )
        }
        loadUserDetails()
    }

    private fun saveName(name: String) {
        setState { copy(name = name) }
        checkIfButtonsEnable()
        if (validationUseCase.checkIfNameValid(state.value.name)) {
            setState { copy(isNameValid = true) }
        } else {
            setState { copy(isNameValid = false, isEnable = false) }
        }
    }

    private fun saveSurname(surname: String) {
        setState { copy(surname = surname) }
        checkIfButtonsEnable()
        if (validationUseCase.checkIfNameValid(state.value.surname)) {
            setState { copy(isSurnameValid = true) }
        } else {
            setState { copy(isSurnameValid = false, isEnable = false) }
        }
    }

    private fun saveGender(gender: Int) {
        setState { copy(gender = gender) }
        checkIfButtonsEnable()
    }

    private fun saveEmail(email: String) {
        setState { copy(email = email) }
        checkIfButtonsEnable()
        if (validationUseCase.checkIfEmailValid(state.value.email)) {
            setState { copy(isEmailValid = true) }
        } else {
            setState { copy(isEmailValid = false, isEnable = false) }
        }
    }

    private fun savePatronymic(patronymic: String) {
        setState { copy(patronymic = patronymic) }
        checkIfButtonsEnable()
    }

    private fun saveBirthDate(birthDate: String) {
        setState { copy(birthDate = birthDate) }
        checkIfButtonsEnable()
        if (validationUseCase.checkIfBirthDateValid(state.value.birthDate)) {
            setState { copy(isBirthDateValid = true) }
        } else {
            setState { copy(isBirthDateValid = false, isEnable = false) }
        }
    }

    private fun checkIfButtonsEnable() {
        checkIfCancelButtonEnable()
        checkIfPutButtonEnable()
    }

    private fun checkIfCancelButtonEnable() {
        if (state.value.profileModel != null) {
            if (checkIfDetailsMatches()) {
                setState { copy(isCancelEnable = true) }
            } else {
                setState { copy(isCancelEnable = false) }
            }
        }
    }

    private fun checkIfPutButtonEnable() {
        if (
            state.value.isBirthDateValid &&
            state.value.isEmailValid &&
            state.value.isNameValid
        ) {
            if (state.value.profileModel != null) {
                if (checkIfDetailsMatches()) {
                    setState { copy(isEnable = true) }
                } else {
                    setState { copy(isEnable = false) }
                }
            }
        } else {
            setState { copy(isEnable = false) }
        }
    }

    private fun checkIfDetailsMatches(): Boolean {
        return !validationUseCase.checkIfProfileModelMatches(
            newProfileModel = ProfileModel(
                email = state.value.email,
                name = state.value.name,
                birthDate = state.value.birthDate,
                role = state.value.role,
                isActive = state.value.isActive,
                isDeanWorker = state.value.isDeanWorker,
                isDenied = state.value.isDenied
            ),
            state.value.profileModel!!
        )
    }

    private fun loadUserDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            UserGetProfileUseCase(UserRepository(Network.getUserApi())).invoke().onSuccess { profile ->
                MainScope().launch {
                    setState {
                        copy(
//                            id = profile.id,
                            email = profile.email,
                            name = profile.name,
                            role = profile.role,
                            profileModel = ProfileModel(
                                email = profile.email,
                                name = profile.name,
                                role = profile.role,
                                birthDate = profile.birthDate ?: "01-01-2001",
                                isActive = profile.isActive,
                                isDeanWorker = profile.isDeanWorker,
                                isDenied = profile.isDenied
                            ),
                            birthDate = formatDateUseCase.formatDateFromApi(profile.birthDate ?: "01-01-2001"),
                            isEnable = false,
                            isCancelEnable = false,
                            isLoaded = true,
                            isError = false,
                            isSuccess = true,
                            isNameValid = true,
                            isEmailValid = true,
                            isBirthDateValid = true,
                            isRefreshing = false,
                            isDenied = profile.isDenied,
                            isActive = profile.isActive
                        )
                    }
                }
            }.onFailure { error ->

            }
            setState { copy(isRefreshing = false) }
        }
    }

    private fun putUserDetails(haptic: HapticFeedback) {
        viewModelScope.launch(Dispatchers.Main) {
            UserPutProfileUseCase(UserRepository(Network.getUserApi())).invoke(
                UserProfileRequestBody(
                    name = state.value.name,
                    email = state.value.email,
                    password = state.value.password
                )
            ).onSuccess {
                setState {
                    copy(
                        isEnable = false,
                        isCancelEnable = false,
                        isSuccess = true,
                        profileModel = ProfileModel(
                            email = state.value.email,
                            name = state.value.name,
                            birthDate = state.value.birthDate,
                            role = state.value.role,
                            isActive = state.value.isActive,
                            isDeanWorker = state.value.isDeanWorker,
                            isDenied = state.value.isDenied
                        )
                    )
                }
            }.onFailure {

            }
        }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.Main) {
            UserLogoutUseCase(UserRepository(Network.getUserApi())).invoke()
            MainScope().launch {
                setEffect { ProfileScreenContract.Effect.Navigation.ToIntroducing }
            }
        }
    }
}