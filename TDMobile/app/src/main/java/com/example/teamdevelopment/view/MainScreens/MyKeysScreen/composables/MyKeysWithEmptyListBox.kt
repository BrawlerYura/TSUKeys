package com.example.teamdevelopment.view.MainScreens.MyKeysScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdevelopment.R
import com.example.teamdevelopment.ui.theme.interFamily

@Composable
fun MyKeysWithEmptyListBox(
    onSelectKeysEvent: () -> Unit
) {
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
                text = "Пока что здесь только котик.",
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Забронируйте аудиторию, чтобы получить ключ.",
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 7.dp)
            )

            Text(
                text = "Подобрать аудиторию",
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .clickable { onSelectKeysEvent() }
            )
        }
    }
}

@Composable
fun MyRequestsWithEmptyListBox(
    onSelectKeysEvent: () -> Unit
) {
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
                text = "Пока что здесь только котик.",
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Оставьте заявку на ключ, чтобы она тут появилась.",
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 7.dp)
            )

            Text(
                text = "Подобрать аудиторию",
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .clickable { onSelectKeysEvent() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyKeysWithEmptyListBoxPreview() {
    MyKeysWithEmptyListBox(
        onSelectKeysEvent = {}
    )
}