package com.example.teamdevelopment.navigation.AuthNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.RegistrationScreen
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.RegistrationViewModel
import com.example.teamdevelopment.navigation.Screen
import com.example.teamdevelopment.view.authScreens.RegistrationScreen.RegistrationContract
import org.koin.androidx.compose.getViewModel

@Composable
fun RegistratinoScreenDestination(navController: NavHostController) {
    val viewModel = getViewModel<RegistrationViewModel>()
    RegistrationScreen(
        state = viewModel.state.value,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is RegistrationContract.Effect.Navigation.NextScreen -> {
                    if(navigationEffect.registerRequestBody.patronymic == null || navigationEffect.registerRequestBody.patronymic == "") {
                        navController.navigate(
                            Screen.RegPass.route + "/${navigationEffect.registerRequestBody.name}" +
                                    "/${navigationEffect.registerRequestBody.surname}" +
                                    "/-" +
                                    "/${navigationEffect.registerRequestBody.email}" +
                                    "/${navigationEffect.registerRequestBody.birthDate}" +
                                    "/${navigationEffect.registerRequestBody.gender}"
                        )
                    } else {
                        navController.navigate(
                            Screen.RegPass.route + "/${navigationEffect.registerRequestBody.name}" +
                                    "/${navigationEffect.registerRequestBody.surname}" +
                                    "/${navigationEffect.registerRequestBody.patronymic}" +
                                    "/${navigationEffect.registerRequestBody.email}" +
                                    "/${navigationEffect.registerRequestBody.birthDate}" +
                                    "/${navigationEffect.registerRequestBody.gender}"
                        )
                    }
                }

                is RegistrationContract.Effect.Navigation.ToLogin -> {
                    navController.navigate(Screen.Login.route)
                }

                is RegistrationContract.Effect.Navigation.Back -> {
                    navController.navigateUp()
                }
            }
        }
    )
}