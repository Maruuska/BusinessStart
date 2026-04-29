package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ppmob.ui.theme.RadioCanadaBold
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun OneFounderScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 60.dp, start = 30.dp, end = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn() {
            item {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.info),
                        contentDescription = "",
                        alignment = Alignment.Center,
                        modifier = Modifier.size(60.dp)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFD1E9F6),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "В реальной регистрации вам",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 16.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "потребуется подготовить",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 16.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "«Решение единственного учредителя»",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 16.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "В этом документе необходимо указать:",
                            fontFamily = RadioCanadaBold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "• Свои ФИО, паспортные данные и адрес регистрации",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "А также письменно зафиксировать:",
                            fontFamily = RadioCanadaBold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "• Факт принятия решения о создании ООО",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "• Полное и сокращённое название компании",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "• Юридический адрес (с помещением под «офис»)",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "• Размер уставного капитала (минимум 10 000 ₽)",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "• Срок, в который вы внесёте уставный капитал",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "• Утверждение Устава компании (основного документа)",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(45.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ButtonCustom(
                        "Понятно",
                        true,
                        ActiveBlue,
                        NoActiveBlue,
                        16.sp,
                        14.dp,
                        160.dp,
                        45.dp
                    ) {
                        navController.popBackStack()
                    }
                }
                Column(
                    modifier = Modifier
                        .height(50.dp)
                        .background(Color.Transparent)
                ) { }
            }
        }
    }
}