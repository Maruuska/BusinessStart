package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.artguess.presentation.navigation.NavRoutes


import kotlinx.coroutines.delay

// экран загрузки
@Composable
fun SplashScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().background(SplashGreen),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LaunchedEffect(true) {
            delay(1200L)
            navController.navigate(NavRoutes.signin){
                popUpTo(NavRoutes.splash){
                    inclusive = true
                }
            }
        }
        Image(
            painter = painterResource(id= R.drawable.crocodile100),
            contentDescription = "",
            alignment = Alignment.Center,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ArtGuess", fontFamily = OpenSansFamily, fontSize = 20.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(10.dp))
        CustomLinearProgressBar()
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}