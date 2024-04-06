package com.example.teamdevelopment.view.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdevelopment.R
import com.example.teamdevelopment.ui.theme.MyTypography
import com.example.teamdevelopment.ui.theme.interFamily

@Composable
fun MyPasswordTextFieldBox(
    value: String,
    isError: Boolean,
    isValid: Boolean,
    onSaveEvent: (text: String) -> Unit,
    headerText: String,
    errorText: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = headerText,
            style = MyTypography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        var isTextHidden by remember { mutableStateOf(true) }
        OutlinedTextField(
            value = value,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = if (isTextHidden) PasswordVisualTransformation() else VisualTransformation.None,
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                errorContainerColor = Color(0xFFE64646).copy(alpha = 0.1f)
            ),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                onSaveEvent(it)
            },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { isTextHidden = !isTextHidden }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id =
                            if (isTextHidden) {
                                R.drawable.opened_eye
                            } else {
                                R.drawable.closed_eye
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )

        if (isValid) {
            Text(
                text = errorText,
                style = MyTypography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordTextBoxPreview() {
    MyPasswordTextFieldBox(
        value = "value",
        isError = true,
        isValid = false,
        onSaveEvent = { },
        headerText = "Пароль",
        errorText = "Неверный пароль"
    )
}