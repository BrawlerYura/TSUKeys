package com.example.teamdevelopment.view.authScreens.RegistrationPasswordScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teamdevelopment.ui.theme.TeamDevelopmentTheme
import com.example.teamdevelopment.view.authScreens.LoginScreen.Composables.LoginHeader
import com.example.teamdevelopment.view.authScreens.RegistrationPasswordScreen.RegistrationPasswordContract
import com.example.teamdevelopment.R
import com.example.teamdevelopment.domain.entities.RequestBodies.RegisterRequestBody
import com.example.teamdevelopment.ui.theme.MyTypography
import com.example.teamdevelopment.view.base.SIDE_EFFECTS_KEY
import com.example.teamdevelopment.view.common.MyButton
import com.example.teamdevelopment.view.common.MyPasswordTextFieldBox
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun RegistrationPasswordScreen(
    state: RegistrationPasswordContract.State,
    onEventSent: (event: RegistrationPasswordContract.Event) -> Unit,
    effectFlow: Flow<RegistrationPasswordContract.Effect>?,
    onNavigationRequested: (navigation: RegistrationPasswordContract.Effect.Navigation) -> Unit,
    registerRequestBody: RegisterRequestBody
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is RegistrationPasswordContract.Effect.Navigation.ToMain -> onNavigationRequested(
                    effect
                )

                is RegistrationPasswordContract.Effect.Navigation.ToLogin -> onNavigationRequested(
                    effect
                )

                is RegistrationPasswordContract.Effect.Navigation.Back -> onNavigationRequested(
                    effect
                )
            }
        }?.collect()
    }
    TeamDevelopmentTheme {
        when {
            state.isError -> {

            }

            else -> {
                RegisterPasswordScreenInner(state, onEventSent, registerRequestBody)
            }
        }

    }
}

@Composable
fun RegisterPasswordScreenInner(
    state: RegistrationPasswordContract.State,
    onEventSent: (event: RegistrationPasswordContract.Event) -> Unit,
    registerRequestBody: RegisterRequestBody
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.95f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoginHeader(text = stringResource(R.string.app_name), onNavigationRequested = { onEventSent(RegistrationPasswordContract.Event.NavigationBack) })

            Text(
                text = stringResource(R.string.registration_button),
                style = MyTypography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 15.dp)
            )

            MyPasswordTextFieldBox(
                value = state.password,
                isError = state.isSuccess == false,
                isValid = false,
                onSaveEvent = { text ->
                    onEventSent(RegistrationPasswordContract.Event.SavePasswordEvent(text))
                },
                headerText = stringResource(R.string.password_label),
                errorText = state.errorMessage ?: ""
            )

            MyPasswordTextFieldBox(
                value = state.repPassword,
                isError = state.isSuccess == false,
                isValid = state.isSuccess == false,
                onSaveEvent = { text ->
                    onEventSent(
                        RegistrationPasswordContract.Event.SaveRepeatedPasswordEvent(text)
                    )
                },
                headerText = stringResource(R.string.repeat_password),
                errorText = state.errorMessage ?: ""
            )

            val haptic = LocalHapticFeedback.current
            MyButton(
                isEnabled = state.isPasswordValid == true && state.isRepPasswordValid == true,
                onEventSent = {
                    onEventSent(
                        RegistrationPasswordContract.Event.SignUp(
                            haptic,
                            registerRequestBody
                        )
                    )
                },
                text = stringResource(R.string.finish_registration_button),
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(32.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
        BottomRegistrationText { onEventSent(RegistrationPasswordContract.Event.NavigationToLogin) }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistrationPasswordScreenPreview() {
    RegistrationPasswordScreen(
        state = repeatPasswordStatePreview,
        onEventSent = { },
        effectFlow = null,
        onNavigationRequested = { },
        registerRequestBody = RegisterRequestBody(
            name = "",
            surname = "",
            patronymic = "",
            password = "",
            email = "",
            birthDate = "",
            gender = 0
        )
    )
}

val repeatPasswordStatePreview = RegistrationPasswordContract.State(
    password = "password",
    repPassword = "password",
    isSuccess = true,
    isPasswordValid = true,
    isRepPasswordValid = true,
    errorMessage = null,
    isLoading = false,
    isBodyLoaded = false,
    isError = false
)