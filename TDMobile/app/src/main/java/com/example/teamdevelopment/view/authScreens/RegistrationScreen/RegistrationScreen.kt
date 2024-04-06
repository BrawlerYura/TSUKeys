package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teamdevelopment.ui.theme.TeamDevelopmentTheme
import com.example.teamdevelopment.view.authScreens.LoginScreen.Composables.LoginHeader
import com.example.teamdevelopment.view.authScreens.RegistrationPasswordScreen.Composables.BottomRegistrationText
import com.example.teamdevelopment.R
import com.example.teamdevelopment.domain.entities.RequestBodies.RegisterRequestBody
import com.example.teamdevelopment.ui.theme.MyTypography
import com.example.teamdevelopment.view.authScreens.RegistrationScreen.RegistrationContract
import com.example.teamdevelopment.view.common.ChooseGenderBox
import com.example.teamdevelopment.view.common.MyBirthDateTextBox
import com.example.teamdevelopment.view.common.MyButton
import com.example.teamdevelopment.view.common.MyTextFieldBox

@Composable
fun RegistrationScreen(
    state: RegistrationContract.State,
    onEventSent: (event: RegistrationContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: RegistrationContract.Effect.Navigation) -> Unit
) {
    TeamDevelopmentTheme {
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = spacedBy(15.dp)
            ) {

                LoginHeader (onNavigationRequested = { onNavigationRequested(RegistrationContract.Effect.Navigation.Back) }, text = stringResource(R.string.app_name))

                Text(
                    text = stringResource(R.string.registration_button),
                    style = MyTypography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                MyTextFieldBox(
                    value = state.name,
                    isError = false,
                    isValid = state.isNameValid == false,
                    onSaveEvent = { text ->
                        onEventSent(RegistrationContract.Event.SaveNameEvent(text))
                    },
                    headerText = stringResource(R.string.name_label),
                    errorText = stringResource(R.string.invalid_name_message)
                )

                MyTextFieldBox(
                    value = state.surname,
                    isError = false,
                    isValid = state.isSurnameValid == false,
                    onSaveEvent = { text ->
                        onEventSent(RegistrationContract.Event.SaveSurnameEvent(text))
                    },
                    headerText = stringResource(R.string.surname_label),
                    errorText = stringResource(R.string.invalid_surname_message)
                )

                MyTextFieldBox(
                    value = state.patronymic,
                    isError = false,
                    isValid = state.isPatronymicValid == false,
                    onSaveEvent = { text ->
                        onEventSent(RegistrationContract.Event.SavePatronymicEvent(text))
                    },
                    headerText = "Номер факультета",
                    errorText = "Введите номер факультета"
                )

                ChooseGenderBox(
                    currentIndex = state.gender,
                    onSaveEvent = { gender ->
                        onEventSent(RegistrationContract.Event.SaveGenderEvent(gender))
                    }
                )

                MyTextFieldBox(
                    value = state.email,
                    isError = false,
                    isValid = state.isEmailValid == false,
                    onSaveEvent = { text ->
                        onEventSent(RegistrationContract.Event.SaveEmailEvent(text))
                    },
                    headerText = stringResource(R.string.email_label),
                    errorText = stringResource(R.string.invalid_email_message)
                )

                MyBirthDateTextBox(
                    value = state.birthDate,
                    text = stringResource(R.string.birth_date),
                    isValid = state.isBirthDateValid == false,
                    isError = false,
                    onSaveDateEvent = { date ->
                        onEventSent(
                            RegistrationContract.Event.SaveBirthDateWithFormatEvent(
                                date
                            )
                        )
                    },
                    onSaveTextEvent = { text ->
                        onEventSent(
                            RegistrationContract.Event.SaveBirthDateEvent(text)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                MyButton(
                    isEnabled = state.isBirthDateValid == true &&
                            state.isSurnameValid == true &&
                            state.isEmailValid == true &&
                            state.isNameValid == true,
                    onEventSent = {
                        onNavigationRequested(
                            RegistrationContract.Effect.Navigation.NextScreen(
                                registerRequestBody = RegisterRequestBody(
                                    name = state.name,
                                    surname = state.surname,
                                    patronymic = state.patronymic,
                                    password = "",
                                    email = state.email,
                                    birthDate = state.apiBirthDate,
                                    gender = state.gender
                                )
                            )
                        )
                    },
                    text = stringResource(R.string.continue_button),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.height(5.dp))
            }
            BottomRegistrationText {
                onNavigationRequested(RegistrationContract.Effect.Navigation.ToLogin)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreen(
        state = registrationStatePreview,
        onEventSent = { },
        onNavigationRequested = { }
    )
}

val registrationStatePreview = RegistrationContract.State(
    name = "Cherry",
    surname = "Tomatoes",
    patronymic = "Cherry Tomatoes",
    email = "sis.sas@gmail.com",
    birthDate = "01.01.2001",
    gender = 0,
    apiBirthDate = "",
    isNameValid = false,
    isSurnameValid = false,
    isPatronymicValid = false,
    isEmailValid = false,
    isBirthDateValid = false
)