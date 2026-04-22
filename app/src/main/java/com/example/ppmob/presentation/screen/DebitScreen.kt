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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.ButtonCustom2
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.ActiveGreen
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.NoActiveGreen
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun DebitScreen(navController: NavHostController) {

    // Состояния для отслеживания нажатий кнопок
    var act1 by remember { mutableStateOf(true) }
    var act2 by remember { mutableStateOf(true) }
    var act3 by remember { mutableStateOf(true) }
    val isAllSelected = !act1 && !act2 && !act3

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 80.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Сбор дебиторской задолженности",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(35.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(55.dp)
                        .background(ActiveBlue)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Дебиторы — это те, кто должен компании. Соберите долги, чтобы увеличить ликвидационную массу. Чем больше удастся вернуть, тем больше средств останется для выплат участникам после расчётов с кредиторами",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 12.sp,
                    color = Color(0xFF696969),
                    textAlign = TextAlign.Left,
                    lineHeight = 14.sp,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.watch),
                    contentDescription = "",
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "2 месяца с момента размещения информации о ликвидации и отправки уведомлений",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 11.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(start = 12.dp),
                    lineHeight = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(35.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Дебитор",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Сумма (₽)",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Действие",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            // Строка 1
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "ООО «Ромашка»",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "150 000",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f).padding(start = 22.dp)
                )
                ButtonCustom2(
                    label = "Сформировать акт сверки",
                    enbl = act1,
                    activeColor = ActiveGreen,
                    noActiveColor = NoActiveGreen,
                    fontSize = 9.sp,
                    rounded = 14.dp,
                    buttonWidth = 120.dp,
                    buttonHeight = 35.dp
                ) {
                    act1 = false
                }
            }
            Divider(color = Color.LightGray, thickness = 1.dp)

            // Строка 2
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "ИП Петров",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "50 000",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f).padding(start = 22.dp)
                )
                ButtonCustom2(
                    label = "Сформировать акт сверки",
                    enbl = act2,
                    activeColor = ActiveGreen,
                    noActiveColor = NoActiveGreen,
                    fontSize = 9.sp,
                    rounded = 14.dp,
                    buttonWidth = 120.dp,
                    buttonHeight = 35.dp
                ) {
                    act2 = false
                }
            }
            Divider(color = Color.LightGray, thickness = 1.dp)

            // Строка 3
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "ООО «Сникерс»",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "98 000",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f).padding(start = 22.dp)
                )
                ButtonCustom2(
                    label = "Сформировать акт сверки",
                    enbl = act3,
                    activeColor = ActiveGreen,
                    noActiveColor = NoActiveGreen,
                    fontSize = 9.sp,
                    rounded = 14.dp,
                    buttonWidth = 120.dp,
                    buttonHeight = 35.dp
                ) {
                    act3 = false
                }
            }
            Divider(color = Color.LightGray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Если выяснится, что имущества недостаточно, дальнейшее прекращение деятельности фирмы возможно только в рамках процедуры несостоятельности (банкротства)",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(start = 12.dp),
                    lineHeight = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(45.dp))

            ButtonCustom(
                label = "Далее",
                enbl = isAllSelected,
                activeColor = ActiveBlue,
                noActiveColor = NoActiveBlue,
                fontSize = 16.sp,
                rounded = 14.dp,
                buttonWidth = 200.dp,
                buttonHeight = 45.dp
            ) {
                navController.navigate(NavRoutes.menu)
            }

        }
    }
}