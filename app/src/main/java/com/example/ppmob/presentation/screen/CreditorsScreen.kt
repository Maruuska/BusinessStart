package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.ppmob.presentation.components.TextFieldDigital
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreditorsScreen(navController: NavHostController) {

    var userAnswer by remember { mutableStateOf("") }
    var showResult by remember { mutableStateOf(false) }
    var isAnswerCorrect by remember { mutableStateOf(false) }
    val scope = remember { CoroutineScope(Dispatchers.Main) }
    val correctAnswer = "3124"

    fun checkAnswer() {
        val isCorrect = userAnswer == correctAnswer
        isAnswerCorrect = isCorrect
        showResult = true

        if (isCorrect) {
            scope.launch {
                delay(2000)
                navController.navigate(NavRoutes.packageLiq)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 70.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Расчёт с кредиторами",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(35.dp))

            LazyColumn {
                item {
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
                            text = "Долги раздаются в соответствии с составленным промежуточным балансом и очередью, установленной ст. 64 ГК РФ.",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.padding(start = 12.dp),
                            lineHeight = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(35.dp))

                    Text(
                        text = "Перед вами — список кредиторов в случайном порядке. Расставьте их в правильной очерёдности",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(start = 12.dp),
                        lineHeight = 15.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    // Заголовки с одинаковыми весами
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Номер",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                        Text(
                            text = "Кредитор",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(2f)
                                .padding(horizontal = 8.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                        Text(
                            text = "Сумма (₽)",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }

                    // Строка 1
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "1",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                        Text(
                            text = "Зарплата сотрудников",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(2f)
                                .padding(horizontal = 8.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                        Text(
                            text = "200 000",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color.LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Строка 2
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "2",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                        Text(
                            text = "Налоги",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(2f)
                                .padding(horizontal = 8.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                        Text(
                            text = "50 000",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color.LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Строка 3
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "3",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                        Text(
                            text = "Вред жизни и здоровью",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(2f)
                                .padding(horizontal = 8.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                        Text(
                            text = "20 000",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color.LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Строка 4
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "4",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                        Text(
                            text = "Прочие кредиторы",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(2f)
                                .padding(horizontal = 8.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                        Text(
                            text = "100 000",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color.LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Введите ответ, например: 1234",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TextFieldDigital(
                        value = userAnswer,
                        onvaluechange = { newValue ->
                            userAnswer = newValue
                            if (showResult) {
                                showResult = false
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        ButtonCustom(
                            "Проверить",
                            userAnswer.isNotEmpty(),
                            ActiveBlue,
                            NoActiveBlue,
                            16.sp,
                            14.dp,
                            150.dp,
                            45.dp
                        ) {
                            checkAnswer()
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    if (showResult) {
                        if (isAnswerCorrect) {
                            Text(
                                text = "Решение верно!",
                                fontFamily = RadioCanadaSemiBold,
                                fontSize = 15.sp,
                                color = Color(0xFF7DC23F),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Column(
                                modifier = Modifier
                                    .height(50.dp)
                                    .background(Color.Transparent)
                            ) { }
                        } else {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Решение не верно!",
                                    fontFamily = RadioCanadaSemiBold,
                                    fontSize = 15.sp,
                                    color = Color.Red,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = "Правильная очерёдность (ст. 64 ГК РФ):",
                                    fontFamily = RadioCanadaMedium,
                                    fontSize = 13.sp,
                                    color = Color.Black,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Text(
                                    text = "3 → Вред жизни и здоровью\n1 → Зарплата сотрудников\n2 → Налоги\n4 → Прочие кредиторы",
                                    fontFamily = RadioCanadaRegular,
                                    fontSize = 12.sp,
                                    color = Color(0xFF696969),
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.fillMaxWidth(),
                                    lineHeight = 22.sp
                                )
                            }

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
}