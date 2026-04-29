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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.viewmodel.RegulationViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaBold
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun RegulationScreen(
    navController: NavHostController,
    regulationViewModel: RegulationViewModel = hiltViewModel(),
) {
    val stateField by regulationViewModel.fields.collectAsState()

    // Состояние для выбранной радиокнопки
    var selectedYstavType by remember { mutableStateOf<Boolean>(true) }
    var selectedExitOption by remember { mutableStateOf(0) }

    // Состояние для всплывающего окна инструкции
    var showInstructionDialog by remember { mutableStateOf(true) }

    LaunchedEffect(stateField.ystavType) {
        selectedYstavType = stateField.ystavType
    }

    LaunchedEffect(stateField.exitOption) {
        selectedExitOption = stateField.exitOption
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 65.dp, start = 25.dp, end = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Устав ООО",
                    fontFamily = RadioCanadaSemiBold,
                    fontSize = 19.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(id = R.drawable.help),
                    contentDescription = "",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { showInstructionDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            LazyColumn(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = "Выберите тип устава:",
                        fontFamily = RadioCanadaBold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // типовой устав
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            RadioButton(
                                selected = selectedYstavType == true,
                                onClick = {
                                    selectedYstavType = true
                                    regulationViewModel.updateState(
                                        stateField.copy(ystavType = true)
                                    )
                                },
                                modifier = Modifier.size(20.dp),
                                colors = androidx.compose.material3.RadioButtonDefaults.colors(
                                    selectedColor = Color.Black,
                                    unselectedColor = Color.Black
                                )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Типовой устав",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 13.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(start = 0.dp)
                            )
                        }

                        if (selectedYstavType == true) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 30.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(4.dp)
                                        .height(36.dp)
                                        .background(ActiveBlue)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Готовая форма от ФНС. Вы не можете изменять условия — подходит для простых случаев.",
                                    fontFamily = RadioCanadaRegular,
                                    fontSize = 12.sp,
                                    color = Color(0xFF696969),
                                    textAlign = TextAlign.Left,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Индивидуальный устав
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            RadioButton(
                                selected = selectedYstavType == false,
                                onClick = {
                                    selectedYstavType = false
                                    regulationViewModel.updateState(
                                        stateField.copy(ystavType = false)
                                    )
                                },
                                modifier = Modifier.size(20.dp),
                                colors = androidx.compose.material3.RadioButtonDefaults.colors(
                                    selectedColor = Color.Black,
                                    unselectedColor = Color.Black
                                )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Индивидуальный устав",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 13.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(start = 0.dp)
                            )
                        }

                        if (selectedYstavType == false) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 30.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(4.dp)
                                        .height(70.dp)
                                        .background(ActiveBlue)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Вы сами определяете права участников, порядок выхода, способы голосования. Рекомендуется для бизнеса с иностранным участием.",
                                    fontFamily = RadioCanadaRegular,
                                    fontSize = 12.sp,
                                    color = Color(0xFF696969),
                                    textAlign = TextAlign.Left,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(27.dp))

                    // Блок прав участников
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Права участников",
                                fontFamily = RadioCanadaBold,
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left,
                            )
                            Text(
                                text = "(стандартный набор):",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 13.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Left,
                            )
                        }
                        ButtonCustom(
                            "Дополнить",
                            !selectedYstavType,
                            ActiveBlue,
                            NoActiveBlue,
                            12.sp,
                            16.dp,
                            80.dp,
                            32.dp
                        ) {
                            navController.navigate(NavRoutes.rights)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ok),
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Участвовать в управлении делами общества",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ok),
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Получать информацию о деятельности общества",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ok),
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Принимать участие в распределении прибыли",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ok),
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Получать имущество при ликвидации",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Блок обязанностей участников
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Обязанности участников",
                                fontFamily = RadioCanadaBold,
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left,
                                lineHeight = 14.sp,
                            )
                            Text(
                                text = "(стандартный набор):",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 13.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Left,
                            )
                        }
                        ButtonCustom(
                            "Дополнить",
                            !selectedYstavType,
                            ActiveBlue,
                            NoActiveBlue,
                            12.sp,
                            16.dp,
                            80.dp,
                            32.dp
                        ) {
                            navController.navigate(NavRoutes.duties)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ok),
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Оплачивать доли в уставном капитале",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ok),
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Не разглашать конфиденциальную информацию",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ok),
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Нести субсидиарную ответственность при банкротстве",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Выберите порядок выхода из ООО:",
                        fontFamily = RadioCanadaBold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp), // убрали .height(30.dp)
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        RadioButton(
                            selected = selectedExitOption == 0,
                            onClick = {
                                selectedExitOption = 0
                                regulationViewModel.updateState(
                                    stateField.copy(exitOption = 0)
                                )
                            },
                            modifier = Modifier.size(20.dp),
                            colors = androidx.compose.material3.RadioButtonDefaults.colors(
                                selectedColor = Color.Black,
                                unselectedColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Свободный выход (по заявлению участника)",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color.Black,
                            modifier = Modifier.weight(1f),
                            softWrap = true,
                            lineHeight = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        RadioButton(
                            selected = selectedExitOption == 1,
                            onClick = {
                                selectedExitOption = 1
                                regulationViewModel.updateState(
                                    stateField.copy(exitOption = 1)
                                )
                            },
                            modifier = Modifier.size(20.dp),
                            colors = androidx.compose.material3.RadioButtonDefaults.colors(
                                selectedColor = Color.Black,
                                unselectedColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Выход только с согласия общего собрания",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color.Black,
                            modifier = Modifier.weight(1f),
                            lineHeight = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        RadioButton(
                            selected = selectedExitOption == 2,
                            onClick = {
                                selectedExitOption = 2
                                regulationViewModel.updateState(
                                    stateField.copy(exitOption = 2)
                                )
                            },
                            modifier = Modifier.size(20.dp),
                            colors = androidx.compose.material3.RadioButtonDefaults.colors(
                                selectedColor = Color.Black,
                                unselectedColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Запрет на выход из общества",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(35.dp))

                    ButtonCustom(
                        "Утвердить устав",
                        true,
                        ActiveBlue,
                        NoActiveBlue,
                        16.sp,
                        14.dp,
                        270.dp,
                        45.dp
                    ) {
                        navController.navigate(NavRoutes.step4)
                    }

                    Column(
                        modifier = Modifier
                            .height(50.dp)
                            .background(Color.Transparent)
                    ) { }
                }
            }
        }

        // Всплывающее окно инструкции
        if (showInstructionDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false) { },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Это обучающая имитация создания устава ООО.",
                        fontFamily = RadioCanadaBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Что нужно сделать на этом шаге:",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "1. Выберите тип устава:\n" +
                                "   • Типовой — готовая форма ФНС\n" +
                                "   • Индивидуальный — с возможностью добавлять свои права и обязанности\n\n" +
                                "2. При выборе индивидуального устава:\n" +
                                "   • Нажмите кнопку «Дополнить» рядом с правами/обязанностями\n" +
                                "   • Вы сможете добавить дополнительные условия\n\n" +
                                "3. Укажите порядок выхода участников из ООО\n\n" +
                                "4. Нажмите «Утвердить устав» для завершения этапа\n\n" +
                                "💡 В реальной регистрации устав — это главный документ компании. Он должен содержать:\n" +
                                "   • Полное и сокращённое наименование ООО\n" +
                                "   • Юридический адрес\n" +
                                "   • Права и обязанности участников\n" +
                                "   • Порядок выхода из ООО и другие условия",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Left,
                        lineHeight = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "💡 Важно!",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 12.sp,
                        color = ActiveBlue,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Типовой устав удобен, но ограничивает свободу выбора. Индивидуальный устав даёт больше гибкости, особенно при участии иностранцев.",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Left,
                        lineHeight = 16.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ButtonCustom(
                        "Понятно",
                        true,
                        ActiveBlue,
                        NoActiveBlue,
                        14.sp,
                        12.dp,
                        180.dp,
                        42.dp
                    ) {
                        showInstructionDialog = false
                    }
                }
            }
        }
    }
}