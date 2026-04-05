package com.example.artguess.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ppmob.presentation.screen.DocumentScreen
import com.example.ppmob.presentation.screen.MenuScreen
import com.example.ppmob.presentation.screen.OfficeScreen
import com.example.ppmob.presentation.screen.OneFounderScreen
import com.example.ppmob.presentation.screen.PalataScreen
import com.example.ppmob.presentation.screen.SeveralFounderScreen
import com.example.ppmob.presentation.screen.SplashScreen
import com.example.ppmob.presentation.screen.Step11Screen
import com.example.ppmob.presentation.screen.Step1Screen
import com.example.ppmob.presentation.screen.Step21Screen
import com.example.ppmob.presentation.screen.Step2Screen
import com.example.ppmob.presentation.screen.Step31Screen
import com.example.ppmob.presentation.screen.Step3Screen

@Composable
fun NavigHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.step3
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
        composable(route = NavRoutes.oneFounder) {
            OneFounderScreen(navController = navController)
        }
        composable(route = NavRoutes.severalFounder) {
            SeveralFounderScreen(navController = navController)
        }
        composable(route = NavRoutes.step2) {
            Step2Screen(navController = navController)
        }
        composable(route = NavRoutes.step21) {
            Step21Screen(navController = navController)
        }
        composable(route = NavRoutes.palata) {
            PalataScreen(navController = navController)
        }
        composable(
            route = NavRoutes.docWithParam,
            arguments = listOf(
                navArgument("needApostille") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val needApostille = backStackEntry.arguments?.getBoolean("needApostille") ?: false
            DocumentScreen(
                navController = navController,
                needApostille = needApostille
            )
        }
        composable(route = NavRoutes.step3) {
            Step3Screen(navController = navController)
        }
        composable(route = NavRoutes.step31) {
            Step31Screen(navController = navController)
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