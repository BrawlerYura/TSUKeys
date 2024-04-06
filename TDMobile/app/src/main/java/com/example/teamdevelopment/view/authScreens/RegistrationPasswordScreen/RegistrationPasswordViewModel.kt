package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen

import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.lifecycle.viewModelScope
import com.example.teamdevelopment.domain.entities.RequestBodies.RegisterRequestBody
import com.example.teamdevelopment.domain.entities.RequestBodies.UserRegisterRequestBody
import com.example.teamdevelopment.domain.useCases.ValidationUseCase
import com.example.teamdevelopment.domain.useCases.dean.DeanGetDeans
import com.example.teamdevelopment.domain.useCases.user.UserChangeRoleUseCase
import com.example.teamdevelopment.domain.useCases.user.UserRegisterUseCase
import com.example.teamdevelopment.network.Network
import com.example.teamdevelopment.network.repository.DeanRepository
import com.example.teamdevelopment.network.repository.UserRepository
import com.example.teamdevelopment.view.authScreens.RegistrationPasswordScreen.RegistrationPasswordContract
import com.example.teamdevelopment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RegistrationPasswordViewModel(
    private val validationUseCase: ValidationUseCase
) : BaseViewModel<
        RegistrationPasswordContract.Event,
        RegistrationPasswordContract.State,
        RegistrationPasswordContract.Effect
        >() {

    private fun savePassword(password: String) {
        setState { copy(password = password) }
        if (validationUseCase.checkIfPasswordValid(password)) {
            setState {
                copy(isPasswordValid = true)
            }
        } else {
            setState {
                copy(isPasswordValid = false)
            }
        }
    }

    private fun saveRepeatedPassword(repeatedPassword: String) {
        setState { copy(repPassword = repeatedPassword) }
        if (validationUseCase.checkIfPasswordValid(repeatedPassword)) {
            setState {
                copy(isRepPasswordValid = true)
            }
        } else {
            setState {
                copy(isRepPasswordValid = false)
            }
        }
    }

    override fun setInitialState() = RegistrationPasswordContract.State(
        password = "",
        repPassword = "",
        isSuccess = null,
        isPasswordValid = null,
        isRepPasswordValid = null,
        isError = false,
        errorMessage = null,
        isBodyLoaded = false,
        isLoading = false
    )

    override fun handleEvents(event: RegistrationPasswordContract.Event) {
        when (event) {
            is RegistrationPasswordContract.Event.SavePasswordEvent -> savePassword(event.password)
            is RegistrationPasswordContract.Event.SaveRepeatedPasswordEvent -> saveRepeatedPassword(
                event.repPassword
            )

            is RegistrationPasswordContract.Event.SignUp -> signUp(
                haptic = event.haptic,
                body = event.body
            )

            is RegistrationPasswordContract.Event.NavigationToLogin -> setEffect { RegistrationPasswordContract.Effect.Navigation.ToLogin }
            is RegistrationPasswordContract.Event.NavigationBack -> setEffect { RegistrationPasswordContract.Effect.Navigation.Back }
        }
    }

    private fun signUp(haptic: HapticFeedback, body: RegisterRequestBody) {
        if (state.value.password != state.value.repPassword) {
            setState { copy(isSuccess = false, errorMessage = "Пароли не совпадают") }
            return
        }

        viewModelScope.launch {
            val result = UserRegisterUseCase(UserRepository(Network.getUserApi())).invoke(
                UserRegisterRequestBody(
                    name = body.name + " " + body.surname,
                    email = body.email,
                    password = state.value.password,
                    birthDate = body.birthDate
                )
            )
            if (result.isSuccess) {
                Network.getTokenManager().setToken(result.getOrNull()!!.token)
                setState { copy(isSuccess = true, isError = false) }
                MainScope().launch {
                    setEffect { RegistrationPasswordContract.Effect.Navigation.ToMain }
                }
                changeRoleRequest(body)

            } else {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                setState {
                    copy(
                        isSuccess = false,
                        errorMessage = "Указаны некорректные данные",
                        isLoading = false,
                        isError = false
                    )
                }
            }
        }
    }

    private fun changeRoleRequest(body: RegisterRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            DeanGetDeans(DeanRepository(Network.getDeanApi())).invoke().onSuccess {
                val deansList = it
                for (dean in deansList) {
                    if (dean.facultyNumber == body.patronymic) {
                        UserChangeRoleUseCase(UserRepository(Network.getUserApi())).invoke(
                            desiredRole = if (body.gender == 0) {
                                "Student"
                            } else {
                                "Teacher"
                            },
                            deanId = dean.id
                        ).onSuccess {

                        }.onFailure {

                        }
                    }
                }
            }.onFailure {

            }
        }
    }
}