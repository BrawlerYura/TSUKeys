package com.example.teamdevelopment.view.authScreens.LoginScreen

import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.lifecycle.viewModelScope
import com.example.teamdevelopment.domain.entities.RequestBodies.UserLoginRequestBody
import com.example.teamdevelopment.domain.entities.RequestBodies.UserRegisterRequestBody
import com.example.teamdevelopment.domain.useCases.ValidationUseCase
import com.example.teamdevelopment.domain.useCases.user.UserLoginUseCase
import com.example.teamdevelopment.domain.useCases.user.UserRegisterUseCase
import com.example.teamdevelopment.network.Network
import com.example.teamdevelopment.network.repository.UserRepository
import com.example.teamdevelopment.view.authScreens.RegistrationPasswordScreen.RegistrationPasswordContract
import com.example.teamdevelopment.view.base.BaseViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validationUseCase: ValidationUseCase,
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    override fun setInitialState() = LoginContract.State(
        email = "",
        password = "",
        isSuccess = null,
        buttonEnabled = false,
        errorMessage = null,
        isLoading = false,
        isError = false
    )

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.SaveLoginEvent -> saveLogin(login = event.login)
            is LoginContract.Event.SavePasswordEvent -> savePassword(password = event.password)
            is LoginContract.Event.SignIn -> signIn(haptic = event.haptic)
            is LoginContract.Event.NavigationToRegistration -> setEffect { LoginContract.Effect.Navigation.ToRegistration }
            is LoginContract.Event.NavigationBack -> setEffect { LoginContract.Effect.Navigation.Back }
        }
    }

    private fun saveLogin(login: String) {
        setState { copy(email = login) }
        checkIfTextBoxesValid()
    }

    private fun savePassword(password: String) {
        setState { copy(password = password) }
        checkIfTextBoxesValid()
    }

    private fun checkIfTextBoxesValid() {
        if (validationUseCase.checkIfLoginValid(state.value.email)
            && validationUseCase.checkIfPasswordValid(state.value.password)
        ) {
            setState {
                copy(
                    buttonEnabled = true
                )
            }
        } else {
            setState {
                copy(
                    buttonEnabled = false
                )
            }
        }
    }

    private fun signIn(haptic: HapticFeedback) {
        viewModelScope.launch {
            val result = UserLoginUseCase(UserRepository(Network.getUserApi())).invoke(
                UserLoginRequestBody(
                    email = state.value.email,
                    password = state.value.password
                )
            )
            if (result.isSuccess) {
                Network.getTokenManager().setToken(result.getOrNull()!!.token)
                setState { copy(isSuccess = true, isError = false) }
                MainScope().launch {
                    setEffect { LoginContract.Effect.Navigation.ToMain }
                }
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
}