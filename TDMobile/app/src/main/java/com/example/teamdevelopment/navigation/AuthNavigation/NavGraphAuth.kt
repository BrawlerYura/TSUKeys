package com.example.teamdevelopment.navigation.AuthNavigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.teamdevelopment.domain.entities.RequestBodies.RegisterRequestBody
import com.example.teamdevelopment.domain.entities.RequestBodies.UserRegisterRequestBody
import com.example.teamdevelopment.navigation.Screen

fun NavGraphBuilder.NavGraphAuth(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Splash.route,
        route = "authentication"
    ) {
        composable(Screen.Splash.route) {
            SplashScreenDestination(navController)
        }
        composable(Screen.Introducing.route) {
            IntroducingScreenDestination(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreenDestination(navController)
        }

        composable(Screen.Registration.route) {
            RegistratinoScreenDestination(navController)
        }

        composable(
            Screen.RegPass.route + "/{name}/{surname}/{patronymic}/{email}/{birthDate}/{gender}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("surname") { type = NavType.StringType },
                navArgument("patronymic") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("birthDate") { type = NavType.StringType },
                navArgument("gender") { type = NavType.IntType }
            )
        ) { entry ->
            val registerRequestBody = RegisterRequestBody(
                name = (entry.arguments?.getString("name") ?: ""),
                surname = (entry.arguments?.getString("surname") ?: ""),
                patronymic = (entry.arguments?.getString("patronymic") ?: ""),
                password = "",
                email = (entry.arguments?.getString("email") ?: ""),
                birthDate = (entry.arguments?.getString("birthDate") ?: ""),
                gender = (entry.arguments?.getInt("gender") ?: 0)
            )
            RegistrationPasswordScreenDestination(navController, registerRequestBody)
        }
    }
    composable(Screen.Splash.route) {
        SplashScreenDestination(navController)
    }
}
