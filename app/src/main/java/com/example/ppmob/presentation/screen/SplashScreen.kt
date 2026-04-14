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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.SplashBack
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().background(SplashBack),
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
            painter = painterResource(id= R.drawable.icon),
            contentDescription = "",
            alignment = Alignment.Center,
            modifier = Modifier.size(130.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Бизнес-Старт", fontFamily = RadioCanadaRegular, fontSize = 28.sp, color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
    }
}