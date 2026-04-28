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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Обучающие сценарии",
            fontFamily = RadioCanadaRegular,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        MenuItem(
            title = "Регистрация бизнеса",
            description = "Заполнение анкеты, проверка требований, нотариальное заверение и составление устава",
            onClick = { navController.navigate(NavRoutes.step1) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        MenuItem(
            title = "Постановка на налоговый учет",
            description = "Сбор документов, апостилирование, заполнение формы 11БС-Учет",
            onClick = { navController.navigate(NavRoutes.accounting) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        MenuItem(
            title = "Открытие счета",
            description = "Выбор банка, изучение требований, заполнение анкеты",
            onClick = { navController.navigate(NavRoutes.score) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        MenuItem(
            title = "Закрытие бизнеса",
            description = "Участие в голосовании, сбор задолженностей, расчет с кредиторами",
            onClick = { navController.navigate(NavRoutes.notification) }
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { navController.navigate(NavRoutes.tests) },
            modifier = Modifier
                .padding(horizontal = 70.dp)
                .height(47.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7DC23F),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Пройти тест",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 15.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun MenuItem(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(ActiveBlue)
            .clickable { onClick() }
            .padding(horizontal = 15.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontFamily = RadioCanadaSemiBold,
                fontSize = 18.sp,
                color = Color.White,
                softWrap = true,
                maxLines = Int.MAX_VALUE
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontFamily = RadioCanadaRegular,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.85f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 12.sp
            )
        }

        Column(
            modifier = Modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.next),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}