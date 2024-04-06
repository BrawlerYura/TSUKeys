package com.example.teamdevelopment.view.authScreens.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.teamdevelopment.ui.theme.TeamDevelopmentTheme
import com.example.teamdevelopment.R
import com.example.teamdevelopment.view.base.SIDE_EFFECTS_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SplashScreen(
    state: SplashContract.State,
    onEventSent: (event: SplashContract.Event) -> Unit,
    effectFlow: Flow<SplashContract.Effect>?,
    onNavigationRequested: (navigationEffect: SplashContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is SplashContract.Effect.Navigation.ToMain -> onNavigationRequested(effect)
                is SplashContract.Effect.Navigation.ToIntroducingScreen -> onNavigationRequested(
                    effect
                )
            }
        }?.collect()
    }

    when {
        state.isNetworkError -> {

        }

        state.isError -> onEventSent(SplashContract.Event.OnTokenLoadedFailed)
        else -> {
            TeamDevelopmentTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tsu_key_logo),
                        contentDescription = "Splash Image",
                        contentScale = ContentScale.FillBounds // Указывает, как изображение будет масштабироваться при отображении
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        state = SplashContract.State(
            isNetworkError = false,
            isError = false
        ),
        onEventSent = { },
        effectFlow = null,
        onNavigationRequested = { }
    )
}