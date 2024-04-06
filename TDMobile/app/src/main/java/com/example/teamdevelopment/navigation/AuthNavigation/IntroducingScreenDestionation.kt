package com.example.teamdevelopment.navigation.AuthNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.teamdevelopment.navigation.Screen
import com.example.teamdevelopment.view.authScreens.IntroducingScreen.Composables.IntroducingScreen
import com.example.teamdevelopment.view.authScreens.IntroducingScreen.IntroducingContract

@Composable
fun IntroducingScreenDestination(navController: NavHostController) {
    IntroducingScreen(
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is IntroducingContract.Effect.Navigation.ToRegistration -> {
                    navController.navigate(Screen.Registration.route)
                }

                is IntroducingContract.Effect.Navigation.ToLogin -> {
                    navController.navigate(Screen.Login.route)
                }
            }
        }
    )
}