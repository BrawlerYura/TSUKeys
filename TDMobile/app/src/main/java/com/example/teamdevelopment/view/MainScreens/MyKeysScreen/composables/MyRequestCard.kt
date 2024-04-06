package com.example.teamdevelopment.view.MainScreens.MyKeysScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdevelopment.R
import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.domain.entities.data.RequestModel
import com.example.teamdevelopment.ui.theme.interFamily

@Composable
fun MyRequestCard(
    request: RequestModel,
    onReturnKeyEvent: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 15.dp, horizontal = 20.dp)
    ) {
        Text(
            text = "Ключ от ${request.key.number} (${request.key.building}) аудитории",
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            textAlign = TextAlign.Start
        )
        val couple = when {
            (request.schedule == "First") -> {
                "первая"
            }
            (request.schedule == "Second") -> {
                "вторая"
            }
            (request.schedule == "Third") -> {
                "третья"
            }
            (request.schedule == "Fourth") -> {
                "четвертая"
            }
            (request.schedule == "Fifth") -> {
                "пятая"
            }
            (request.schedule == "Sixth") -> {
                "шестая"
            }
            else -> {
                "седьмая"
            }
        }
        Text(
            text = "${request.date}, ${couple} пара",
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            textAlign = TextAlign.Start
        )

        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = (MaterialTheme.colorScheme.onSurface).copy(alpha = 0.2f))
        )

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        onReturnKeyEvent()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.keys_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .height(18.dp)
                        .width(18.dp),
                    tint = MaterialTheme.colorScheme.error
                )

                Text(
                    text = "Отменить заявку", style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ), textAlign = TextAlign.Left, color = MaterialTheme.colorScheme.error
                )
            }

//            Row(
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                modifier = Modifier
//                    .align(Alignment.CenterEnd)
//                    .clickable {
//                        onTransferKeyEvent()
//                    }
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.qr_icon),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .height(18.dp)
//                        .width(18.dp),
//                    tint = MaterialTheme.colorScheme.onSurface
//                )
//
//                Text(
//                    text = "Передать ключ", style = TextStyle(
//                        fontFamily = interFamily,
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 12.sp,
//                        color = MaterialTheme.colorScheme.onSurface
//                    ), textAlign = TextAlign.Left, color = MaterialTheme.colorScheme.onSurface
//                )
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyRequestCardPreview() {
    MyRequestCard(
        request = RequestModel(
            id = "",
            key = KeyModel(
                number = 224,
                id = "",
                building = 2,
                currentUser = null,
                currentUserId = null,
                deanId = ""
            ),
            date = "10.10.1001",
            schedule = "First",
            isRepeat = true
        ),
        onReturnKeyEvent = {  },
//        onTransferKeyEvent = {  }
    )
}