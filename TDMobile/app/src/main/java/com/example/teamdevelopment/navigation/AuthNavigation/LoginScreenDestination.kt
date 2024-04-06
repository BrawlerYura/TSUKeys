package com.example.teamdevelopment.navigation.AuthNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.teamdevelopment.navigation.Screen
import com.example.teamdevelopment.view.authScreens.LoginScreen.Composables.LoginScreen
import com.example.teamdevelopment.view.authScreens.LoginScreen.LoginContract
import com.example.teamdevelopment.view.authScreens.LoginScreen.LoginViewModel
import com.example.teamdevelopment.view.authScreens.RegistrationPasswordScreen.RegistrationPasswordContract
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreenDestination(navController: NavHostController) {
    val viewModel = getViewModel<LoginViewModel>()
    LoginScreen(
        state = viewModel.state.value,
        onEventSent = { event -> viewModel.setEvent(event) },
        effectFlow = viewModel.effect,
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is LoginContract.Effect.Navigation.ToMain -> {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Introducing.route) {
                            inclusive = true
                        }
                    }
                }

                is LoginContract.Effect.Navigation.ToRegistration -> {
                    navController.navigate(Screen.Registration.route)
                }

                is LoginContract.Effect.Navigation.Back -> {
                    navController.navigateUp()
                }
            }
        }
    )
}