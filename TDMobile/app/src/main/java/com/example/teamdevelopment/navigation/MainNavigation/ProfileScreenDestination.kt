package com.example.teamdevelopment.navigation.MainNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.teamdevelopment.navigation.Screen
import com.example.teamdevelopment.view.MainScreens.ProfileScreen.ProfileScreenViewModel
import com.example.teamdevelopment.view.MainScreens.ProfileScreen.Composables.ProfileScreen
import com.example.teamdevelopment.view.MainScreens.ProfileScreen.ProfileScreenContract
import com.example.teamdevelopment.view.authScreens.RegistrationScreen.RegistrationContract
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreenDestination(
    navController: NavHostController
) {
    val viewModel = getViewModel<ProfileScreenViewModel>()
    ProfileScreen(
        state = viewModel.state.value,
        onEventSent = { event -> viewModel.setEvent(event) },
        effectFlow = viewModel.effect,
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is ProfileScreenContract.Effect.Navigation.Back -> {
                    navController.navigateUp()
                }

                ProfileScreenContract.Effect.Navigation.ToIntroducing -> {
                    navController.navigate(Screen.Introducing.route) {
                        popUpTo(Screen.Main.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    )
}