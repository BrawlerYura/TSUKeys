package com.example.teamdevelopment.navigation.AuthNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.RegistrationPasswordViewModel
import com.example.teamdevelopment.domain.entities.RequestBodies.RegisterRequestBody
import com.example.teamdevelopment.domain.entities.RequestBodies.UserRegisterRequestBody
import com.example.teamdevelopment.navigation.Screen
import com.example.teamdevelopment.view.authScreens.RegistrationPasswordScreen.Composables.RegistrationPasswordScreen
import com.example.teamdevelopment.view.authScreens.RegistrationPasswordScreen.RegistrationPasswordContract
import org.koin.androidx.compose.getViewModel

@Composable
fun RegistrationPasswordScreenDestination(
    navController: NavHostController,
    registerRequestBody: RegisterRequestBody
) {
    val viewModel = getViewModel<RegistrationPasswordViewModel>()
    RegistrationPasswordScreen(
        state = viewModel.state.value,
        onEventSent = { event -> viewModel.setEvent(event) },
        effectFlow = viewModel.effect,
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is RegistrationPasswordContract.Effect.Navigation.ToMain -> {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Introducing.route) {
                            inclusive = true
                        }
                    }
                }

                is RegistrationPasswordContract.Effect.Navigation.ToLogin -> {
                    navController.navigate(Screen.Login.route)
                }

                is RegistrationPasswordContract.Effect.Navigation.Back -> {
                    navController.navigateUp()
                }
            }
        },
        registerRequestBody
    )
}