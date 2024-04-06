package com.example.teamdevelopment.view.authScreens.LoginScreen.Composables

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
import com.example.teamdevelopment.view.authScreens.LoginScreen.LoginContract
import com.example.teamdevelopment.R
import com.example.teamdevelopment.ui.theme.MyTypography
import com.example.teamdevelopment.view.base.SIDE_EFFECTS_KEY
import com.example.teamdevelopment.view.common.MyButton
import com.example.teamdevelopment.view.common.MyPasswordTextFieldBox
import com.example.teamdevelopment.view.common.MyTextFieldBox
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginScreen(
    state: LoginContract.State,
    onEventSent: (event: LoginContract.Event) -> Unit,
    effectFlow: Flow<LoginContract.Effect>?,
    onNavigationRequested: (navigationEffect: LoginContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is LoginContract.Effect.Navigation.ToMain -> onNavigationRequested(effect)
                is LoginContract.Effect.Navigation.ToRegistration -> onNavigationRequested(effect)
                is LoginContract.Effect.Navigation.Back -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    TeamDevelopmentTheme {
        when {
            state.isError -> {

            }

            else -> {
                LoginScreenInner(state, onEventSent)
            }
        }
    }
}

@Composable
fun LoginScreenInner(
    state: LoginContract.State,
    onEventSent: (event: LoginContract.Event) -> Unit,
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

            LoginHeader (onNavigationRequested = { onEventSent(LoginContract.Event.NavigationBack) }, text = stringResource(R.string.app_name) )

            Text(
                text = stringResource(R.string.login_title),
                style = MyTypography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 15.dp)
            )

            MyTextFieldBox(
                value = state.email,
                isError = state.isSuccess == false,
                isValid = false,
                onSaveEvent = { text -> onEventSent(LoginContract.Event.SaveLoginEvent(text)) },
                headerText = stringResource(R.string.email_label),
                errorText = ""
            )

            Spacer(modifier = Modifier.height(15.dp))

            MyPasswordTextFieldBox(
                value = state.password,
                isError = state.isSuccess == false,
                isValid = state.isSuccess == false,
                onSaveEvent = { text ->
                    onEventSent(LoginContract.Event.SavePasswordEvent(text))
                },
                headerText = stringResource(R.string.password_label),
                errorText = state.errorMessage ?: "",
            )

            val haptic = LocalHapticFeedback.current
            MyButton(
                isEnabled = state.buttonEnabled,
                onEventSent = { onEventSent(LoginContract.Event.SignIn(haptic)) },
                text = stringResource(R.string.login_button),
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
        LoginBottomText { onEventSent(LoginContract.Event.NavigationToRegistration) }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        state = loginStatePreview,
        onEventSent = { },
        onNavigationRequested = { },
        effectFlow = null
    )
}

val loginStatePreview = LoginContract.State(
    email = "myemail@abc.com",
    password = "password",
    isSuccess = null,
    buttonEnabled = true,
    errorMessage = null,
    isLoading = false,
    isError = false
)