package com.example.teamdevelopment.navigation.MainNavigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.teamdevelopment.navigation.Screen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.NavGraphMain(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Main.route,
        route = "main_route"
    ) {
        composable(Screen.Main.route) {
            MainScreenDestination(navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreenDestination(
                navController
            )
        }

        composable(
            Screen.SelectKey.route + "/{date}/{couple}",
            arguments = listOf(
                navArgument("date") { type = NavType.StringType },
                navArgument("couple") { type = NavType.StringType },
            )
        ) { entry ->
            val date = (entry.arguments?.getString("date") ?: "")
            val couple = (entry.arguments?.getString("couple") ?: "")
            SelectKeyScreenDestination(navController, date, couple)
        }

        composable(Screen.MyKeys.route) {
            MyKeysScreenDestination(
                navController
            )
        }
    }
}