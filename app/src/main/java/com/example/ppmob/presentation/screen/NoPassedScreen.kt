package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun NoPassedScreen(testName: String, countBalls: Int,totalQuestions: Int = 6, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(23.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row {
            Image(
                painter = painterResource(R.drawable.notpassed),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Тест \"$testName\" не пройден",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 17.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Ваш балл: $countBalls/$totalQuestions",
            fontFamily = RadioCanadaRegular,
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        ProgressBar(progress = countBalls.toFloat() / totalQuestions.toFloat())
        Spacer(modifier = Modifier.height(45.dp))

        ButtonCustom(
            "Меню",
            true,
            ActiveBlue,
            NoActiveBlue,
            16.sp,
            14.dp,
            230.dp,
            45.dp
        ) {
            navController.navigate(NavRoutes.menu)
        }
    }
}