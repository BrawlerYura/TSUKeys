package com.example.teamdevelopment.view.MainScreens.MainScreen.Composables

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdevelopment.ui.theme.MyTypography
import com.example.teamdevelopment.ui.theme.interFamily
import com.example.teamdevelopment.view.MainScreens.MainScreen.MainContract
import com.example.teamdevelopment.view.common.MyBirthDateTextBox
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookKeysBox(
    state: MainContract.State,
    onSaveDateEvent: (date: LocalDate) -> Unit,
    onSaveCoupleEvent: (index: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            text = "Забронировать ключ",
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )

        val formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(state.selectedDate.toString(), formatterInput)

        val formatterOutput = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formattedDate = date.format(formatterOutput)

        MyBirthDateTextBox(
            value = if(state.selectedDate == LocalDate.now()) "Сегодня" else formattedDate,
            text = "Дата",
            isValid = false,
            isError = false,
            onSaveDateEvent = {
                if (it != null) {
                    onSaveDateEvent(LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000)))
                }
            },
            onSaveTextEvent = {            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Пара",
                style = MyTypography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            for (card in state.lessonCards) {
                LessonCard(card) {
                    onSaveCoupleEvent(it)
                }
            }
        }
    }
}