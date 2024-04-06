package com.example.teamdevelopment.view.MainScreens.MainScreen.Composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.teamdevelopment.ui.theme.TeamDevelopmentTheme
import com.example.teamdevelopment.view.MainScreens.MainScreen.MainContract
import com.example.teamdevelopment.view.base.SIDE_EFFECTS_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    state: MainContract.State,
    onEventSent: (event: MainContract.Event) -> Unit,
    effectFlow: Flow<MainContract.Effect>?,
    onNavigationRequested: (navigationEffect: MainContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is MainContract.Effect.Navigation.ToProfile -> onNavigationRequested(
                    effect
                )
                is MainContract.Effect.Navigation.ToSelectKey -> onNavigationRequested(
                    effect
                )

                is MainContract.Effect.Navigation.ToMyKeys -> onNavigationRequested(
                    effect
                )
            }
        }?.collect()
    }

    TeamDevelopmentTheme {
        when {
            state.isError -> {

            }

            else -> {
                MainScreenInner(state, onEventSent)
            }
        }
    }
}