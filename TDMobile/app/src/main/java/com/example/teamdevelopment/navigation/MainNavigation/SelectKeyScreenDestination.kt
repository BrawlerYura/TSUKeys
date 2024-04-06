package com.example.teamdevelopment.navigation.MainNavigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.teamdevelopment.navigation.Screen
import com.example.teamdevelopment.view.MainScreens.MyKeysScreen.MyKeysScreenContract
import com.example.teamdevelopment.view.MainScreens.selectKeyScreen.Composables.SelectKeyScreen
import com.example.teamdevelopment.view.MainScreens.selectKeyScreen.SelectKeyContract
import com.example.teamdevelopment.view.MainScreens.selectKeyScreen.SelectKeyViewModel
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectKeyScreenDestination(navController: NavHostController, date: String, couple: String) {
    val viewModel = getViewModel<SelectKeyViewModel>()
    SelectKeyScreen(
        state = viewModel.state.value,
        onEventSent = { event -> viewModel.setEvent(event) },
        effectFlow = viewModel.effect,
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is SelectKeyContract.Effect.Navigation.ToMain -> {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.SelectKey.route) {
                            inclusive = true
                        }
                    }
                }
            }
        },
        date = date,
        couple = couple
    )
}