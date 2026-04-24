package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
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
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold


@Composable
fun VtbScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(top = 80.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Назад",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.popBackStack()  // возврат назад без пересоздания экрана
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.vtb),
                contentDescription = "",
                alignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Требования ВТБ по составу документов",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 17.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Документ",
                        fontFamily = RadioCanadaMedium,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Примечание",
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
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Свидетельство о постановке на учет",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Срок не старше 30 дней",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

                // Строка 2
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Выписка из ЕГРЮЛ",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Оригинал или нотариальная копия",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

                // Строка 3
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Устав с отметкой ФНС",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Нотариальная копия",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

                // Строка 4
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Решение о создании ООО / Протокол",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Нотариальная копия",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

                // Строка 5
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Паспорт генерального директора",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Для иностранца — нотариальный перевод",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

                // Строка 6
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Доверенность (если счет открывает не директор)",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "При условии",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

                // Строка 7
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Документ, подтверждающий полномочия директора",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Решение о назначении или приказ с нотариальным переводом",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

                // Строка 8
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Документы о бенефициарах (цепочка собственности до физических лиц)",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Для всех иностранных компаний",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

                // Строка 9
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Документы, подтверждающие легальность пребывания иностранного директора в РФ",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Миграционная карта, регистрация по месту пребывания, ВНЖ/РВП",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

                // Строка 10
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Договор аренды офиса в РФ",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Оригинал или копия",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

                // Строка 11
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Карточка с образцами подписей",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Заверяется нотариусом. Для иностранцев — перевод карточки",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)

                // Строка 12
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Анкета клиента",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Через систему банка",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 15.sp,
                        color = Color(0xFF696969),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}