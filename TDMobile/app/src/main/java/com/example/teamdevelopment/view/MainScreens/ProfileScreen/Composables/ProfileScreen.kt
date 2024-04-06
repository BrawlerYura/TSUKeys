package com.example.teamdevelopment.view.MainScreens.ProfileScreen.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdevelopment.R
import com.example.teamdevelopment.domain.entities.enums.RoleEnum
import com.example.teamdevelopment.domain.entities.enums.StatusEnum
import com.example.teamdevelopment.ui.theme.MyTypography
import com.example.teamdevelopment.ui.theme.TeamDevelopmentTheme
import com.example.teamdevelopment.ui.theme.interFamily
import com.example.teamdevelopment.view.MainScreens.ProfileScreen.ProfileScreenContract
import com.example.teamdevelopment.view.base.SIDE_EFFECTS_KEY
import com.example.teamdevelopment.view.common.ChooseGenderBox
import com.example.teamdevelopment.view.common.MyBirthDateTextBox
import com.example.teamdevelopment.view.common.MyButton
import com.example.teamdevelopment.view.common.MyTextFieldBox
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit,
    effectFlow: Flow<ProfileScreenContract.Effect>?,
    onNavigationRequested: (navigationEffect: ProfileScreenContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(true) {
        onEventSent(ProfileScreenContract.Event.LoadUserDetails)
    }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is ProfileScreenContract.Effect.Navigation.Back -> onNavigationRequested(effect)
                is ProfileScreenContract.Effect.Navigation.ToIntroducing -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    TeamDevelopmentTheme {
//        val refreshState = rememberPullRefreshState(
//            refreshing = state.isRefreshing,
//            onRefresh = {
//                onEventSent(ProfileScreenContract.Event.RefreshScreen)
//            }
//        )
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .pullRefresh(refreshState)
        ) {

            when {
                state.isLoaded -> {
                    ProfileScreenBoxes(state, onEventSent)
                }

                state.isError -> {
//                    NetworkErrorScreen {
//                        onEventSent(ProfileScreenContract.Event.RefreshScreen)
//                    }
                }

                else -> {
                    MainSkeletonScreen()
                }
            }

//            PullRefreshIndicator(
//                refreshing = state.isRefreshing,
//                state = refreshState,
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//            )
        }
    }
}

@Composable
fun ProfileScreenBoxes(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit,
) {

    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = spacedBy(15.dp)
    ) {

        Spacer(Modifier.height(15.dp))

        ProfileBox(state, onNavigationRequested = { onEventSent(ProfileScreenContract.Event.Back) })

        Text(
            text = stringResource(R.string.logout),
            style = MyTypography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    onEventSent(ProfileScreenContract.Event.Logout)
                }
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = spacedBy(15.dp)
        ) {
            MyTextFieldBox(
                value = state.email,
                isError = !state.isSuccess,
                isValid = !state.isEmailValid,
                onSaveEvent = { text ->
                    onEventSent(ProfileScreenContract.Event.SaveEmailEvent(text))
                },
                headerText = stringResource(R.string.email_label),
                errorText = stringResource(R.string.invalid_email_message)
            )

            MyTextFieldBox(
                value = state.name,
                isError = !state.isSuccess,
                isValid = !state.isNameValid,
                onSaveEvent = { text ->
                    onEventSent(ProfileScreenContract.Event.SaveNameEvent(text))
                },
                headerText = "Имя",
                errorText = "Поле имени не должно быть пустым"
            )

//            MyTextFieldBox(
//                value = state.surname,
//                isError = !state.isSuccess,
//                isValid = !state.isSurnameValid,
//                onSaveEvent = { text ->
//                    onEventSent(ProfileScreenContract.Event.SaveSurnameEvent(text))
//                },
//                headerText = "Фамилия",
//                errorText = "Поле фамилии не должно быть пустым"
//            )
//
//            MyTextFieldBox(
//                value = state.patronymic,
//                isError = !state.isSuccess,
//                isValid = false,
//                onSaveEvent = { text ->
//                    onEventSent(ProfileScreenContract.Event.SaveNameEvent(text))
//                },
//                headerText = "Отчество (если есть)",
//                errorText = ""
//            )

//            ChooseGenderBox(
//                currentIndex = state.gender,
//                onSaveEvent = { gender ->
//                    onEventSent(ProfileScreenContract.Event.SaveGenderEvent(gender))
//                }
//            )

            MyBirthDateTextBox(
                value = state.birthDate,
                text = stringResource(R.string.birth_date),
                isValid = !state.isBirthDateValid,
                isError = !state.isSuccess,
                onSaveDateEvent = { date ->
                    onEventSent(
                        ProfileScreenContract.Event.SaveBirthDateWithFormatEvent(
                            date
                        )
                    )
                },
                onSaveTextEvent = { text ->
                    onEventSent(
                        ProfileScreenContract.Event.SaveBirthDateEvent(text)
                    )
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            if (!state.isSuccess) {
                Text(
                    text = state.errorMessage ?: "",
                    style = MyTypography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            val haptic = LocalHapticFeedback.current
            MyButton(
                isEnabled = state.isEnable,
                onEventSent = {
                    onEventSent(
                        ProfileScreenContract.Event.PutNewUserDetails(
                            haptic
                        )
                    )
                },
                text = stringResource(R.string.save),
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            MyButton(
                isEnabled = state.isCancelEnable,
                onEventSent = { onEventSent(ProfileScreenContract.Event.LoadUserDetails) },
                text = stringResource(R.string.refuse),
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        state = profileStatePreview,
        onEventSent = { },
        effectFlow = null,
        onNavigationRequested = { }
    )
}

val profileStatePreview = ProfileScreenContract.State(
    id = "",
    email = "my@email.com",
    name = "my name",
    surname = "my surname",
    patronymic = "my patronymic",
    gender = 0,
    birthDate = "30.07.2004",
    isSuccess = true,
    errorMessage = null,
    profileModel = null,
    isEnable = true,
    isCancelEnable = true,
    isNameValid = true,
    isEmailValid = true,
    isBirthDateValid = true,
    isRefreshing = false,
    isLoaded = true,
    isError = false,
    isSurnameValid = true,
    profileStatus = StatusEnum.Unconfirmed,
    role = RoleEnum.Student,
    password = "",
    isActive = false,
    isDeanWorker = false,
    isDenied = true
)