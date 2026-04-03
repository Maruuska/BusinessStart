package com.example.artguess.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ppmob.presentation.screen.SplashScreen

@Composable
fun NavigHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.splash
    ) {
        composable(route = NavRoutes.splash) {
            SplashScreen(navController = navController)
        }
//        composable(route = NavRoutes.signin) {
//            SignInScreen(navController = navController)
//        }
//        composable(route = NavRoutes.main) {
//            MainScreen(navController = navController)
//        }
//        composable(route = NavRoutes.signup) {
//            SignUpScreen(navController = navController)
//        }
    }
}