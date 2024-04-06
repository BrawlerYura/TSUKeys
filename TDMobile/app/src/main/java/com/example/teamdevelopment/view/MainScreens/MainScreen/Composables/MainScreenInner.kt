package com.example.teamdevelopment.view.MainScreens.MainScreen.Composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdevelopment.domain.entities.enums.RoleEnum
import com.example.teamdevelopment.domain.entities.enums.StatusEnum
import com.example.teamdevelopment.ui.theme.MyTypography
import com.example.teamdevelopment.ui.theme.interFamily
import com.example.teamdevelopment.view.MainScreens.MainScreen.MainContract
import com.example.teamdevelopment.view.MainScreens.selectKeyScreen.SelectKeyContract
import com.example.teamdevelopment.view.base.SIDE_EFFECTS_KEY
import com.example.teamdevelopment.view.common.MyButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenInner(
    state: MainContract.State,
    onEventSent: (event: MainContract.Event) -> Unit
) {

    val functionExecuted = remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (!functionExecuted.value) {
            onEventSent(MainContract.Event.LoadProfile())
            functionExecuted.value = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 15.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Text(
//                text = "ГЛАВНАЯ СТРАНИЦА",
//                style = MyTypography.bodyLarge,
//                textAlign = TextAlign.Center,
//                color = MaterialTheme.colorScheme.onSurface,
//            )

            Spacer(modifier = Modifier.height(15.dp))

            if (state.profile != null) {
                MyCard(
                    myFullName = state.profile.name,
                    myRole = state.profile.role,
                    navigateToMyKeys = { onEventSent(MainContract.Event.NavigateToMyKeys()) },
                    navigateToProfile = { onEventSent(MainContract.Event.NavigateToProfile()) },
                    isActive = state.profile.isActive,
                    isDenied = state.profile.isDenied
                )
            } else {
                MyCard(
                    myFullName = "Иванов Иван Иванович",
                    myRole = RoleEnum.Student,
                    navigateToMyKeys = { onEventSent(MainContract.Event.NavigateToMyKeys()) },
                    navigateToProfile = { onEventSent(MainContract.Event.NavigateToProfile()) },
                    isActive = true,
                    isDenied = false
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            BookKeysBox(
                state,
                onSaveDateEvent = { onEventSent(MainContract.Event.SaveDateEvent(it)) },
                onSaveCoupleEvent = { onEventSent(MainContract.Event.SaveCoupleEvent(it)) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            MyButton(
                isEnabled = ((state.selectedCouple != -1) && (state.profile != null) && (state.profile.isActive) && (!state.profile.isDenied)),
                onEventSent = { onEventSent(MainContract.Event.NavigateToSelectKey()) },
                text = "Подобрать ключи",
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))
            if (state.profile != null && state.profile.role == RoleEnum.Unconfirmed) {
                Box(Modifier.fillMaxSize()) {
                    Text(
                        text = "аккаунт не подтвержден, подойтите к деканату",
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.error
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    )
                }
            } else {
                if (state.profile != null && state.profile.isDenied) {
                    Box(Modifier.fillMaxSize()) {
                        Text(
                            text = "заявка на подтверждение аккаунта отклонена",
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.error
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                        )
                    }
                } else {
                    if (state.profile != null && !state.profile.isDenied && !state.profile.isActive) {
                        Box(Modifier.fillMaxSize()) {
                            Text(
                                text = "аккаунт не подтвержден, ваша заявка еще не рассмотрена",
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.error
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreenInnerPreview() {
    MainScreenInner(
        state = MainContract.State(
            isLoading = false,
            isError = false,
            lessonCards = listOf(
                com.example.teamdevelopment.domain.entities.data.LessonCard(
                    "1 пара · 8:45-10:20",
                    isSelected = false,
                    isEnabled = true,
                    index = 0
                ),
                com.example.teamdevelopment.domain.entities.data.LessonCard(
                    "2 пара · 10:35-12:10",
                    isSelected = false,
                    isEnabled = true,
                    index = 0
                ),
                com.example.teamdevelopment.domain.entities.data.LessonCard(
                    "3 пара · 12:25-14:00",
                    isSelected = false,
                    isEnabled = true,
                    index = 0
                ),
                com.example.teamdevelopment.domain.entities.data.LessonCard(
                    "4 пара · 14:45-16:20",
                    isSelected = false,
                    isEnabled = true,
                    index = 0
                ),
                com.example.teamdevelopment.domain.entities.data.LessonCard(
                    "5 пара · 16:35-18:10",
                    isSelected = false,
                    isEnabled = true,
                    index = 0
                ),
                com.example.teamdevelopment.domain.entities.data.LessonCard(
                    "6 пара · 18:25-20:00",
                    isSelected = false,
                    isEnabled = true,
                    index = 0
                )
            ),
            selectedDate = LocalDate.of(2023, 1, 1),
            selectedCouple = 0,
            profile = null
        ),
        onEventSent = { },
    )
}