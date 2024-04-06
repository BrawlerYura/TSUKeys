package com.example.teamdevelopment.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.teamdevelopment.navigation.AuthNavigation.NavGraphAuth
import com.example.teamdevelopment.navigation.MainNavigation.NavGraphMain


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        route = "root"
    )
    {
        NavGraphAuth(navController)
        NavGraphMain(
            navController
        )
    }
}

sealed class Screen(val route: String) {
    object Splash : Screen(route = "splash_screen")
    object Introducing : Screen(route = "introducing_screen")
    object Login : Screen(route = "login_screen")
    object Registration : Screen(route = "registration_screen")
    object RegPass : Screen(route = "reg_pass_screen")
    object Main : Screen(route = "main_screen")
    object Profile : Screen(route = "profile_screen")
    object SelectKey : Screen(route = "select_key_screen")
    object MyKeys : Screen(route = "my_keys_screen")
}