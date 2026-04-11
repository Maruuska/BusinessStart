package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ResultScoreScreen(navController: NavHostController) {
    val currentDate = remember {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        dateFormat.format(Date())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF353835))
            .padding(start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = " Счет открыт!",
            fontFamily = RadioCanadaRegular,
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.salut2),
            contentDescription = "",
            alignment = Alignment.Center,
            modifier = Modifier.size(70.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "\uD83D\uDCC5 Дата открытия: $currentDate",
            fontFamily = RadioCanadaMedium,
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "\uD83D\uDCB3 Номер счета: 40802***********",
            fontFamily = RadioCanadaMedium,
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Что дальше?",
            fontFamily = RadioCanadaMedium,
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "• Вы получите доступ в интернет-банк (на электронную почту)",
            fontFamily = RadioCanadaRegular,
            fontSize = 15.sp,
            color = Color.White,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp)
        )
        Text(
            text = "• Для проведения первой операции потребуется визит в отделение",
            fontFamily = RadioCanadaRegular,
            fontSize = 15.sp,
            color = Color.White,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp)
        )
        Text(
            text = "• Уведомите налоговую об открытии счета (в течение 7 дней)",
            fontFamily = RadioCanadaRegular,
            fontSize = 15.sp,
            color = Color.White,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))


        Spacer(modifier = Modifier.height(90.dp))
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