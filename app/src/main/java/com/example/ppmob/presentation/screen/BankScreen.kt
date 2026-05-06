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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.OutlinedTextFieldDigital
import com.example.ppmob.presentation.components.OutlinedTextFieldDigitalNoEdit
import com.example.ppmob.presentation.viewmodel.BankViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.ActiveGreen
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.NoActiveGreen
import com.example.ppmob.ui.theme.RadioCanadaBold
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold
import kotlinx.coroutines.delay

@Composable
fun BankScreen(
    navController: NavHostController,
    bankViewModel: BankViewModel = hiltViewModel(),
) {
    val stateField by bankViewModel.fieldsBank.collectAsState()

    // Состояния для отслеживания нажатий кнопок
    var isApprove by remember { mutableStateOf(false) }
    val sumValue = stateField.sum.toIntOrNull() ?: 0
    val isSumValid = sumValue >= 10000 && stateField.sum.isNotEmpty()

    var isPay by remember { mutableStateOf(false) }
    var navigate by remember { mutableStateOf(false) }

    // Состояние для диалога с инструкцией
    var showInstructionDialog by remember { mutableStateOf(true) }

    LaunchedEffect(navigate) {
        if (navigate) {
            delay(1000L)
            navController.navigate(NavRoutes.packageRegistration)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 60.dp, start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Формирование уставного",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 19.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                painter = painterResource(id = R.drawable.question),
                contentDescription = "Инструкция",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        showInstructionDialog = true
                    }
            )
        }
        Text(
            text = "капитала",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 19.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Сумма уставного капитала:",
            fontFamily = RadioCanadaRegular,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextFieldDigital(stateField.sum) {
            bankViewModel.updateState(
                stateField.copy(sum = it)
            )
            // сброс состояния утверждения при изменении суммы
            if (isApprove) {
                isApprove = false
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start)
        ) {
            ButtonCustom(
                "15 000 ₽",
                true,
                Color(0xFF6D716D),
                Color(0xFF6D716D),
                14.sp,
                15.dp,
                90.dp,
                35.dp
            ) {
                bankViewModel.updateState(stateField.copy(sum = "15000"))
            }

            ButtonCustom(
                "20 000 ₽",
                true,
                Color(0xFF6D716D),
                Color(0xFF6D716D),
                14.sp,
                15.dp,
                90.dp,
                35.dp
            ) {
                bankViewModel.updateState(stateField.copy(sum = "20000"))
            }

            ButtonCustom(
                "25 000 ₽",
                true,
                Color(0xFF6D716D),
                Color(0xFF6D716D),
                14.sp,
                15.dp,
                90.dp,
                35.dp
            ) {
                bankViewModel.updateState(stateField.copy(sum = "25000"))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        val sumValue = stateField.sum.toIntOrNull() ?: 0

        if (sumValue < 10000 && stateField.sum.isNotEmpty()) {
            Text(
                text = "Минимальный размер уставного капитала для ООО с иностранным учредителем - 10000 ₽",
                fontFamily = RadioCanadaRegular,
                fontSize = 13.sp,
                color = Color.Red,
                textAlign = TextAlign.Left,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        ButtonCustom(
            "Утвердить",
            isSumValid && !isApprove,
            if (isApprove) ActiveGreen else ActiveGreen,
            NoActiveGreen,
            16.sp,
            14.dp,
            270.dp,
            45.dp
        ) {
            if (isSumValid) {
                isApprove = true
            }
            if (isApprove && isPay) {
                navigate = true
            }
        }

        if (isApprove) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Сумма уставного капитала утверждена",
                fontFamily = RadioCanadaRegular,
                fontSize = 13.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text = "Оплата госпошлины:",
            fontFamily = RadioCanadaRegular,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextFieldDigitalNoEdit(stateField.nalog) {
            bankViewModel.updateState(
                stateField.copy(nalog = it)
            )
            isPay = true
        }
        Spacer(modifier = Modifier.height(20.dp))

        ButtonCustom(
            "Оплатить",
            !isPay,
            ActiveGreen,
            NoActiveGreen,
            16.sp,
            14.dp,
            270.dp,
            45.dp
        ) {
            isPay = true
            if (isApprove && isPay) {
                navigate = true
            }
        }

        if (isPay) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Госпошлина оплачена",
                fontFamily = RadioCanadaRegular,
                fontSize = 13.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }

    if (showInstructionDialog) {
        Dialog(
            onDismissRequest = { showInstructionDialog = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
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
                    Text(
                        text = "Инструкция",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "1. Утверждение уставного капитала",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• Введите сумму уставного капитала (минимум 10 000 ₽ для ООО с иностранным учредителем)\n" +
                                "• Используйте кнопки-подсказки для быстрого выбора суммы\n" +
                                "• Нажмите «Утвердить» после ввода корректной суммы",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color(0xFF555555),
                        lineHeight = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "2. Оплата госпошлины",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• Сумма госпошлины — 4 000 рублей\n" +
                                "• Рекомендуем приложить подтверждение уплаты пошлины к пакету документов\n" +
                                "• Госпошлина не уплачивается при подаче заявления через нотариуса или в электронном виде с помощью ЭЦП\n" +
                                "• Нажмите «Оплатить» после подтверждения",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color(0xFF555555),
                        lineHeight = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "⚠️ Для перехода к следующему шагу необходимо утвердить уставный капитал и оплатить госпошлину.",
                            fontFamily = RadioCanadaMedium,
                            fontSize = 12.sp,
                            color = Color(0xFFE6A000),
                            lineHeight = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    ButtonCustom(
                        "Понятно",
                        true,
                        ActiveBlue,
                        NoActiveBlue,
                        14.sp,
                        12.dp,
                        120.dp,
                        40.dp
                    ) {
                        showInstructionDialog = false
                    }
                }
            }
        }
    }
}