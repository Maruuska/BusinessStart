package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold
import kotlinx.coroutines.delay

@Composable
fun PackageLiquidationScreen(navController: NavHostController) {

    // Состояние для диалога
    var showDialog by remember { mutableStateOf(false) }
    // Состояние для обратного отсчета
    var countdown by remember { mutableStateOf(5) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 100.dp, start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Итоговый пакет документов",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 19.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(100.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.p11001),
                    contentDescription = "",
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Окончательный ликвидационный баланс",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.reshenie),
                    contentDescription = "",
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Заявление по форме Р15016",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sertificat),
                    contentDescription = "",
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Квитанция об оплате госпошлины (800 ₽)",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dogovor),
                    contentDescription = "",
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Справка из СФР",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
        ButtonCustom(
            "Закрыть компанию",
            true,
            ActiveBlue,
            NoActiveBlue,
            16.sp,
            14.dp,
            230.dp,
            45.dp
        ) {
            showDialog = true
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    GifImage()
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = when (countdown) {
                            5 -> "5 дней"
                            4 -> "4 дня"
                            3 -> "3 дня"
                            2 -> "2 дня"
                            1 -> "1 день"
                            else -> ""
                        },
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    LaunchedEffect(showDialog) {
        // отсчет
        for (i in 5 downTo 1) {
            countdown = i
            delay(1000L)
        }
        if (showDialog) {
            showDialog = false
            navController.navigate(NavRoutes.endStatement)
        }
    }
}