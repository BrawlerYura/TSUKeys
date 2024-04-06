package com.example.teamdevelopment.view.authScreens.LoginScreen.Composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teamdevelopment.R
import com.example.teamdevelopment.ui.theme.MyTypography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginHeader(
    onNavigationRequested: () -> Unit,
    text: String
) {
    Box(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(24.dp)
    ) {
        var isClickable by remember { mutableStateOf(true) }

        IconButton(
            onClick = {
                if (isClickable) {
                    isClickable = false
                    onNavigationRequested()
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1000L)
                        isClickable = true
                    }
                }
            },
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterStart),
            content = {
                Icon(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .height(12.dp)
                        .width(12.dp)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Text(
            text = text,
            style = MyTypography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginHeaderPreview() {
        LoginHeader(text = stringResource(R.string.app_name), onNavigationRequested = { })
}