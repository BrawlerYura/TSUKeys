package com.example.teamdevelopment.view.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.teamdevelopment.ui.theme.MyTypography

@Composable
fun MyButton(
    isEnabled: Boolean,
    onEventSent: () -> Unit,
    text: String,
    backgroundColor: Color,
    contentColor: Color
) {
    Button(
        onClick = {
            onEventSent()
        },
        enabled = isEnabled,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor.copy(alpha = 0.5f),
            disabledContentColor = contentColor.copy(alpha = 0.5f)
        ),
    ) {
        Text(
            text = text,
            style = MyTypography.titleSmall,
        )
    }
}