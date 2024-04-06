package com.example.teamdevelopment.view.MainScreens.selectKeyScreen.Composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.teamdevelopment.R
import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.domain.entities.enums.RoleEnum
import com.example.teamdevelopment.ui.theme.TeamDevelopmentTheme
import com.example.teamdevelopment.ui.theme.interFamily
import com.example.teamdevelopment.view.MainScreens.selectKeyScreen.SelectKeyContract
import com.example.teamdevelopment.view.authScreens.LoginScreen.Composables.LoginHeader
import com.example.teamdevelopment.view.base.SIDE_EFFECTS_KEY
import com.example.teamdevelopment.view.common.MyButton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectKeyScreen(
    state: SelectKeyContract.State,
    onEventSent: (event: SelectKeyContract.Event) -> Unit,
    effectFlow: Flow<SelectKeyContract.Effect>?,
    onNavigationRequested: (navigationEffect: SelectKeyContract.Effect.Navigation) -> Unit,
    date: String,
    couple: String
) {
    val functionExecuted = remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (!functionExecuted.value) {
            onEventSent(SelectKeyContract.Event.LoadDateAndCouple(date, couple))
            onEventSent(SelectKeyContract.Event.LoadKeysList(date, couple.toInt()))
            onEventSent(SelectKeyContract.Event.LoadProfile)
            functionExecuted.value = true
        }
    }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is SelectKeyContract.Effect.Navigation.ToMain -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    TeamDevelopmentTheme {
        when {
            state.isError -> {

            }

            else -> {
                SelectKeyScreenInner(state, onEventSent)
            }
        }
    }
}

@Composable
fun SelectKeyScreenInner(
    state: SelectKeyContract.State,
    onEventSent: (event: SelectKeyContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            LoginHeader(onNavigationRequested = { }, text = "ПРОФИЛЬ")

            OutlinedTextField(
                value = state.textValue,
                colors = OutlinedTextFieldDefaults.colors(
                    errorContainerColor = Color(0xFFE64646).copy(alpha = 0.1f)
                ),
                textStyle = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp
                ),
                onValueChange = {
                    onEventSent(SelectKeyContract.Event.SaveTextValue(it))
                },
                singleLine = true,
                trailingIcon = {
                    if (state.textValue.isNotEmpty()) {
                        IconButton(onClick = {
                            onEventSent(SelectKeyContract.Event.SaveTextValue(""))
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        if(state.keyListWithFilter.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 13.dp)){
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Image(
                        painter = painterResource(R.drawable.cat),
                        contentDescription = null,
                        modifier = Modifier
                            .height(222.dp)
                            .width(333.dp),
                    )

                    Text(
                        text = "Ключи не найдены",
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                items(state.keyListWithFilter) { key ->
                    KeyItem(key, state, onEventSent = onEventSent)
                }
            }
        }

    }
}

@Composable
fun KeyItem(
    key: KeyModel,
    state: SelectKeyContract.State,
    onEventSent: (event: SelectKeyContract.Event) -> Unit,
) {
    val showDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable { showDialog.value = true }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = key.number.toString() + " " + "(${key.building})",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = "Компьютерный класс",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Start
                )
            }

            Text(
                text = "Free",
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Box(modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(color = (MaterialTheme.colorScheme.primary).copy(alpha = .2f)))
    }
    if (showDialog.value) {
        KeyDialog(
            onCloseDialog = { showDialog.value = false },
            state,
            key.number,
            onEventSent,
            key.id
        )
    }
}

@Composable
fun KeyDialog(
    onCloseDialog: () -> Unit,
    state: SelectKeyContract.State,
    number: Int,
    onEventSent: (event: SelectKeyContract.Event) -> Unit,
    keyId: String
) {
    Dialog(onDismissRequest = onCloseDialog) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(14.dp)),
            color = MaterialTheme.colorScheme.background
        ) {
            val checked = remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column() {
                    Text(
                        text = "Ваша заявка:",
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth()
                    )

                    Text(
                        text = "Дата: ${state.date}",
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth()
                    )
                    when {
                        state.couple == 0 -> {
                            Text(
                                text = "1 пара · 8:45-10:20",
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                        state.couple == 1 -> {
                            Text(
                                text = "2 пара · 10:35-12:10",
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                        state.couple == 2 -> {
                            Text(
                                text = "3 пара · 12:25-14:00",
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                        state.couple == 3 -> {
                            Text(
                                text = "4 пара · 14:45-16:20",
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                        state.couple == 4 -> {
                            Text(
                                text = "5 пара · 16:35-18:10",
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                        else -> {
                            Text(
                                text = "6 пара · 18:25-20:00",
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }

                    Text(
                        text = "Номер ключа: ${number}",
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth()
                    )

                    if(state.profile != null && state.profile.role == RoleEnum.Teacher){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(horizontal = 14.dp)
                        ) {
                            Checkbox(
                                checked = checked.value,
                                onCheckedChange = { checked.value = !checked.value },
                                modifier = Modifier.size(24.dp)
                            )

                            Text(
                                text = "Повторяющийся запрос",
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }

                MyButton(
                    isEnabled = true,
                    onEventSent = { onEventSent(SelectKeyContract.Event.RequestKey(keyId, state.couple, state.date.toString(), checked.value)) },
                    text = "отправить заявку",
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KeyDialogPreview() {
    KeyDialog(
        onCloseDialog = { },
        state = SelectKeyContract.State (
            isLoading = false,
            isError = false,
            date = LocalDate.now(),
            couple = 0,
            textValue = "224",
            keyList = listOf(),
            keyListWithFilter = listOf(),
            profile = null
        ),
        number = 224,
        onEventSent = {},
        keyId = "sad"
    )
}


@Preview(showBackground = true)
@Composable
fun SelectKeyScreenInnerPreview() {
    SelectKeyScreenInner(
        state = SelectKeyContract.State (
            isLoading = false,
            isError = false,
            date = LocalDate.now(),
            couple = 0,
            textValue = "224",
            keyList = listOf(
                KeyModel(
                    number = 224,
                    id = "",
                    building = 2,
                    currentUser = null,
                    currentUserId = null,
                    deanId = ""
                ),
                KeyModel(
                    number = 224,
                    id = "",
                    building = 2,
                    currentUser = null,
                    currentUserId = null,
                    deanId = ""
                ),KeyModel(
                    number = 224,
                    id = "",
                    building = 2,
                    currentUser = null,
                    currentUserId = null,
                    deanId = ""
                ),KeyModel(
                    number = 224,
                    id = "",
                    building = 2,
                    currentUser = null,
                    currentUserId = null,
                    deanId = ""
                ),
            ),
        keyListWithFilter = listOf(
            KeyModel(
                number = 224,
                id = "",
                building = 2,
                currentUser = null,
                currentUserId = null,
                deanId = ""
            ),KeyModel(
                number = 224,
                id = "",
                building = 2,
                currentUser = null,
                currentUserId = null,
                deanId = ""
            ),
            KeyModel(
                number = 224,
                id = "",
                building = 2,
                currentUser = null,
                currentUserId = null,
                deanId = ""
            ),KeyModel(
                number = 224,
                id = "",
                building = 2,
                currentUser = null,
                currentUserId = null,
                deanId = ""
            ),KeyModel(
                number = 224,
                id = "",
                building = 2,
                currentUser = null,
                currentUserId = null,
                deanId = ""
            ),
        ),
            profile = null
    ),
        onEventSent = { }
    )
}