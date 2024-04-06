package com.example.teamdevelopment.view.authScreens.IntroducingScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teamdevelopment.ui.theme.TeamDevelopmentTheme
import com.example.teamdevelopment.R
import com.example.teamdevelopment.view.authScreens.IntroducingScreen.IntroducingContract
import com.example.teamdevelopment.view.common.MyButton

@Composable
fun IntroducingScreen(
    onNavigationRequested: (navigationEffect: IntroducingContract.Effect.Navigation) -> Unit
) {
    TeamDevelopmentTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageWithDescriptionBox()

                MyButton(
                    isEnabled = true,
                    onEventSent = { onNavigationRequested(IntroducingContract.Effect.Navigation.ToRegistration) },
                    text = stringResource(R.string.registration_button),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.height(15.dp))

                MyButton(
                    isEnabled = true,
                    onEventSent = { onNavigationRequested(IntroducingContract.Effect.Navigation.ToLogin) },
                    text = stringResource(R.string.login_button),
                    backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IntroducingScreenPreview() {
    IntroducingScreen(
        onNavigationRequested = { }
    )
}