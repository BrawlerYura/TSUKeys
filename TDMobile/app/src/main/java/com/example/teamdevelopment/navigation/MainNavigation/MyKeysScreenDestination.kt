package com.example.teamdevelopment.navigation.MainNavigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.teamdevelopment.navigation.Screen
import com.example.teamdevelopment.view.MainScreens.MyKeysScreen.MyKeysScreen
import com.example.teamdevelopment.view.MainScreens.MyKeysScreen.MyKeysScreenContract
import com.example.teamdevelopment.view.MainScreens.MyKeysScreen.MyKeysScreenViewModel
import com.example.teamdevelopment.view.MainScreens.selectKeyScreen.Composables.SelectKeyScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun MyKeysScreenDestination(navController: NavHostController) {
    val viewModel = getViewModel<MyKeysScreenViewModel>()
    MyKeysScreen(
        state = viewModel.state.value,
        onEventSent = { event -> viewModel.setEvent(event) },
        effectFlow = viewModel.effect,
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is MyKeysScreenContract.Effect.Navigation.Back -> { navController.navigateUp() }
                is MyKeysScreenContract.Effect.Navigation.ToMainScreen -> {
                    navController.navigate(Screen.Main.route)
                }
            }
        }
    )
}