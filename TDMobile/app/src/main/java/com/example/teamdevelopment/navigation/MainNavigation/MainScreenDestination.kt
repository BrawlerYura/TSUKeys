package com.example.teamdevelopment.navigation.MainNavigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.teamdevelopment.navigation.Screen
import com.example.teamdevelopment.view.MainScreens.MainScreen.Composables.MainScreen
import com.example.teamdevelopment.view.MainScreens.MainScreen.MainContract
import com.example.teamdevelopment.view.MainScreens.MainScreen.MainViewModel
import com.example.teamdevelopment.view.authScreens.LoginScreen.Composables.LoginScreen
import com.example.teamdevelopment.view.authScreens.LoginScreen.LoginContract
import com.example.teamdevelopment.view.authScreens.LoginScreen.LoginViewModel
import com.example.teamdevelopment.view.authScreens.RegistrationScreen.RegistrationContract
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenDestination(navController: NavHostController) {
    val viewModel = getViewModel<MainViewModel>()
    MainScreen(
        state = viewModel.state.value,
        onEventSent = { event -> viewModel.setEvent(event) },
        effectFlow = viewModel.effect,
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is MainContract.Effect.Navigation.ToProfile -> {
                    navController.navigate(Screen.Profile.route)
                }
                is MainContract.Effect.Navigation.ToSelectKey -> {
                    navController.navigate(Screen.SelectKey.route + "/${navigationEffect.date}/${navigationEffect.couple}")
                }

                is MainContract.Effect.Navigation.ToMyKeys -> {
                    navController.navigate(Screen.MyKeys.route)
                }
            }
        }
    )
}