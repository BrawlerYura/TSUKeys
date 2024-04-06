package com.example.teamdevelopment.view.MainScreens.MainScreen.Composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdevelopment.domain.entities.data.LessonCard
import com.example.teamdevelopment.ui.theme.MyTypography
import com.example.teamdevelopment.ui.theme.interFamily
import java.time.LocalDate
import kotlin.math.log

@Composable
fun LessonCard(
    card: LessonCard,
    onSaveCoupleEvent: (index: Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(
                color =
                if (card.isEnabled)
                    if(card.isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.secondaryContainer
                else
                    (MaterialTheme.colorScheme.secondaryContainer).copy(alpha = 0.5f)
            )
            .clickable {
                if(card.isEnabled) {
                    onSaveCoupleEvent(card.index)
                }
            }
    ) {
        Text(
            text = card.text,
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color =
                if (card.isEnabled)
                    if(card.isSelected)
                       MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurface
                else
                    (MaterialTheme.colorScheme.onSurface).copy(alpha = 0.5f)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}