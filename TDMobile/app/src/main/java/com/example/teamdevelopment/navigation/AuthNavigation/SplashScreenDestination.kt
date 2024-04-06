package com.example.teamdevelopment.navigation.AuthNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen.SplashScreenViewModel
import com.example.teamdevelopment.view.authScreens.SplashScreen.SplashContract
import com.example.teamdevelopment.navigation.Screen
import com.example.teamdevelopment.view.authScreens.SplashScreen.SplashScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreenDestination(navController: NavHostController) {
    val viewModel = getViewModel<SplashScreenViewModel>()
    SplashScreen(
        state = viewModel.state.value,
        onEventSent = { event -> viewModel.setEvent(event) },
        effectFlow = viewModel.effect,
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is SplashContract.Effect.Navigation.ToIntroducingScreen -> {
                    navController.popBackStack()
                    navController.navigate(Screen.Introducing.route)
                }

                is SplashContract.Effect.Navigation.ToMain -> {
                    navController.popBackStack()
                    navController.navigate(Screen.Main.route)
                }
            }
        }
    )
}