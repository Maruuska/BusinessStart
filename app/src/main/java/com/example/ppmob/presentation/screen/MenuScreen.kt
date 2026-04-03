package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun MenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 65.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Услуги для иностранных",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 18.sp,
            color = Color.Black
        )
        Text(
            text = "компаний в России",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 18.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(50.dp))

        MenuItem(
            text = "Регистрация бизнеса",
            onClick = { navController.navigate(NavRoutes.step1) }
        )

        Spacer(modifier = Modifier.height(30.dp))

        MenuItem(
            text = "Постановка на налоговый учет",
            onClick = { /* TODO: навигация */ }
        )

        Spacer(modifier = Modifier.height(30.dp))

        MenuItem(
            text = "Открытие счета",
            onClick = { /* TODO: навигация */ }
        )

        Spacer(modifier = Modifier.height(30.dp))

        MenuItem(
            text = "Закрытие бизнеса",
            onClick = { /* TODO: навигация */ }
        )
    }
}

@Composable
fun MenuItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(horizontal = 30.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(ActiveBlue)
            .clickable { onClick() }
            .padding(start = 30.dp, end = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontFamily = RadioCanadaSemiBold,
            fontSize = 17.sp,
            color = Color.White
        )
        Image(
            painter = painterResource(id = R.drawable.next),
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
    }
}