package com.example.artguess.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ppmob.presentation.screen.MenuScreen
import com.example.ppmob.presentation.screen.OfficeScreen
import com.example.ppmob.presentation.screen.SplashScreen
import com.example.ppmob.presentation.screen.Step11Screen
import com.example.ppmob.presentation.screen.Step1Screen

@Composable
fun NavigHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.office
    ) {
        composable(route = NavRoutes.splash) {
            SplashScreen(navController = navController)
        }
        composable(route = NavRoutes.menu) {
            MenuScreen(navController = navController)
        }
        composable(route = NavRoutes.step1) {
            Step1Screen(navController = navController)
        }
        composable(route = NavRoutes.step11) {
            Step11Screen(navController = navController)
        }
        composable(route = NavRoutes.office) {
            OfficeScreen(navController = navController)
        }



//        composable(route = NavRoutes.signin) {
//            SignInScreen(navController = navController)
//        }
//
//        composable(route = NavRoutes.signup) {
//            SignUpScreen(navController = navController)
//        }
    }
}