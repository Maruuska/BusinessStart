package com.example.artguess.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ppmob.presentation.screen.AccountingScreen
import com.example.ppmob.presentation.screen.AlfaScreen
import com.example.ppmob.presentation.screen.AnketaScreen
import com.example.ppmob.presentation.screen.ApostilScreen
import com.example.ppmob.presentation.screen.BalanceScreen
import com.example.ppmob.presentation.screen.BankScreen
import com.example.ppmob.presentation.screen.CreditorsScreen
import com.example.ppmob.presentation.screen.DebitScreen
import com.example.ppmob.presentation.screen.DocumentScreen
import com.example.ppmob.presentation.screen.DutiesScreen
import com.example.ppmob.presentation.screen.MeetingScreen
import com.example.ppmob.presentation.screen.MenuScreen
import com.example.ppmob.presentation.screen.NoPassedScreen
import com.example.ppmob.presentation.screen.NotificationScreen
import com.example.ppmob.presentation.screen.NotifyScreen
import com.example.ppmob.presentation.screen.OfficeScreen
import com.example.ppmob.presentation.screen.OneFounderScreen
import com.example.ppmob.presentation.screen.OtkritieScreen
import com.example.ppmob.presentation.screen.PackageLiquidationScreen
import com.example.ppmob.presentation.screen.PackageScreen
import com.example.ppmob.presentation.screen.PackageStatementScreen
import com.example.ppmob.presentation.screen.PalataScreen
import com.example.ppmob.presentation.screen.PassedScreen
import com.example.ppmob.presentation.screen.RaiffeisenScreen
import com.example.ppmob.presentation.screen.RegulationScreen
import com.example.ppmob.presentation.screen.ResultCompanyScreen
import com.example.ppmob.presentation.screen.ResultLiquidationScreen
import com.example.ppmob.presentation.screen.ResultScoreScreen
import com.example.ppmob.presentation.screen.ResultStatementScreen
import com.example.ppmob.presentation.screen.RightScreen
import com.example.ppmob.presentation.screen.SberScreen
import com.example.ppmob.presentation.screen.ScoreScreen
import com.example.ppmob.presentation.screen.SeveralFounderScreen
import com.example.ppmob.presentation.screen.SignInScreen
import com.example.ppmob.presentation.screen.SignUpScreen
import com.example.ppmob.presentation.screen.SplashScreen
import com.example.ppmob.presentation.screen.StaffScreen
import com.example.ppmob.presentation.screen.StatementScreen
import com.example.ppmob.presentation.screen.Step11Screen
import com.example.ppmob.presentation.screen.Step1Screen
import com.example.ppmob.presentation.screen.Step21Screen
import com.example.ppmob.presentation.screen.Step2Screen
import com.example.ppmob.presentation.screen.Step31Screen
import com.example.ppmob.presentation.screen.Step3Screen
import com.example.ppmob.presentation.screen.Step41Screen
import com.example.ppmob.presentation.screen.Step4Screen
import com.example.ppmob.presentation.screen.TbankScreen
import com.example.ppmob.presentation.screen.TestDetailScreen
import com.example.ppmob.presentation.screen.TestsScreen
import com.example.ppmob.presentation.screen.VotingScreen
import com.example.ppmob.presentation.screen.VtbScreen

@Composable
fun NavigHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.accounting
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
                navArgument("needApostille") { type = NavType.BoolType },
                navArgument("isSimplified") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val needApostille = backStackEntry.arguments?.getBoolean("needApostille") ?: false
            val isSimplified = backStackEntry.arguments?.getBoolean("isSimplified") ?: false
            DocumentScreen(
                navController = navController,
                needApostille = needApostille,
                isSimplified = isSimplified
            )
        }
        composable(route = NavRoutes.step3) {
            Step3Screen(navController = navController)
        }
        composable(route = NavRoutes.step31) {
            Step31Screen(navController = navController)
        }
        composable(route = NavRoutes.regulation) {
            RegulationScreen(navController = navController)
        }
        composable(route = NavRoutes.rights) {
            RightScreen(navController = navController)
        }
        composable(route = NavRoutes.duties) {
            DutiesScreen(navController = navController)
        }
        composable(route = NavRoutes.step4) {
            Step4Screen(navController = navController)
        }
        composable(route = NavRoutes.step41) {
            Step41Screen(navController = navController)
        }
        composable(route = NavRoutes.bank) {
            BankScreen(navController = navController)
        }
        composable(route = NavRoutes.packageRegistration) {
            PackageScreen(navController = navController)
        }
        composable(route = NavRoutes.endCompany) {
            ResultCompanyScreen(navController = navController)
        }
        composable(route = NavRoutes.accounting) {
            AccountingScreen(navController = navController)
        }
        composable(route = NavRoutes.apostil) {
            ApostilScreen(navController = navController)
        }
        composable(route = NavRoutes.statement) {
            StatementScreen(navController = navController)
        }
        composable(route = NavRoutes.packageStatement) {
            PackageStatementScreen(navController = navController)
        }
        composable(route = NavRoutes.endStatement) {
            ResultStatementScreen(navController = navController)
        }
        composable(route = NavRoutes.score) {
            ScoreScreen(navController = navController)
        }
        composable(NavRoutes.sberScreen) {
            SberScreen(navController)
        }
        composable(NavRoutes.tbankScreen) {
            TbankScreen(navController)
        }
        composable(NavRoutes.alfaScreen) {
            AlfaScreen(navController)
        }
        composable(NavRoutes.vtbScreen) {
            VtbScreen(navController)
        }
        composable(NavRoutes.raiffeisenScreen) {
            RaiffeisenScreen(navController)
        }
        composable(NavRoutes.otkritieScreen) {
            OtkritieScreen(navController)
        }
        composable(NavRoutes.anketa) {
            AnketaScreen(navController)
        }
        composable(NavRoutes.endScore) {
            ResultScoreScreen(navController)
        }
        composable(route = NavRoutes.signin) {
            SignInScreen(navController = navController)
        }
        composable(route = NavRoutes.signup) {
            SignUpScreen(navController = navController)
        }
        composable(route = NavRoutes.notification) {
            NotificationScreen(navController = navController)
        }
        composable(route = NavRoutes.votingScreen) {
            VotingScreen(navController = navController)
        }
        composable(route = NavRoutes.meeting) {
            MeetingScreen(navController = navController)
        }
        composable(route = NavRoutes.notify) {
            NotifyScreen(navController = navController)
        }
        composable(route = NavRoutes.debit) {
            DebitScreen(navController = navController)
        }
        composable(route = NavRoutes.balance) {
            BalanceScreen(navController = navController)
        }
        composable(route = NavRoutes.staff) {
            StaffScreen(navController = navController)
        }
        composable(route = NavRoutes.creditors) {
            CreditorsScreen(navController = navController)
        }
        composable(route = NavRoutes.packageLiq) {
            PackageLiquidationScreen(navController = navController)
        }
        composable(route = NavRoutes.resultLiq) {
            ResultLiquidationScreen(navController = navController)
        }
        composable(route = NavRoutes.tests) {
            TestsScreen(navController = navController)
        }
        composable(
            route = NavRoutes.detailTest + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            val id = it.arguments?.getString("id")
            if (id != null) {
                TestDetailScreen(navController = navController,id)
            }
        }
        composable(NavRoutes.passed + "/{testName}/{score}/{total}") { backStackEntry ->
            val testName = backStackEntry.arguments?.getString("testName")?.let { name ->
                java.net.URLDecoder.decode(name, "UTF-8")
            } ?: "Тест"
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            val total = backStackEntry.arguments?.getString("total")?.toIntOrNull() ?: 6

            PassedScreen(
                testName = testName,
                countBalls = score,
                totalQuestions = total,
                navController = navController
            )
        }

        composable(NavRoutes.noPassed + "/{testName}/{score}/{total}") { backStackEntry ->
            val testName = backStackEntry.arguments?.getString("testName")?.let { name ->
                java.net.URLDecoder.decode(name, "UTF-8")
            } ?: "Тест"
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            val total = backStackEntry.arguments?.getString("total")?.toIntOrNull() ?: 6

            NoPassedScreen(
                testName = testName,
                countBalls = score,
                totalQuestions = total,
                navController = navController
            )
        }
    }
}