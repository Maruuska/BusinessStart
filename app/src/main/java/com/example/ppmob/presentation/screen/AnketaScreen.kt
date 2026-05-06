package com.example.ppmob.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.CustomDropDownField
import com.example.ppmob.presentation.components.OutlinedTextFieldNormal
import com.example.ppmob.presentation.components.OutlinedTextFieldNormalDigital
import com.example.ppmob.presentation.viewmodel.AnketaViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun AnketaScreen(
    navController: NavHostController,
    anketaViewModel: AnketaViewModel = hiltViewModel(),
) {
    val stateField by anketaViewModel.fields.collectAsState()
    val appStateSave by anketaViewModel.appStateSave.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 60.dp, start = 25.dp, end = 25.dp, bottom = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Заполните анкету банков",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(40.dp))

        LazyColumn(

        ) {
            item {
                Text(
                    text = "Полное наименование компании",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextFieldNormal(stateField.name) {
                    anketaViewModel.updateState(
                        stateField.copy(name = it)
                    )
                }

                if (stateField.errorName) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Поле обязательно для заполнения",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular,
                            textAlign = TextAlign.Center,
                            lineHeight = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                var expandedCountry by remember { mutableStateOf(false) }
                val selectCountry =
                    anketaViewModel.countries.value?.find { it.id == stateField.countryId }

                Text(
                    text = "Страна регистрации иностранного учредителя",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    CustomDropDownField(
                        value = selectCountry?.name ?: "",
                        placeholder = "Выберите страну",
                        onExpandedChange = { expandedCountry = it }
                    )

                    DropdownMenu(
                        expanded = expandedCountry,
                        onDismissRequest = { expandedCountry = false },
                        modifier = Modifier
                            .background(Color.White)
                            .heightIn(max = 300.dp)
                            .fillMaxWidth(),
                        offset = DpOffset(x = 0.dp, y = 0.dp)
                    ) {
                        anketaViewModel.countries.value!!.forEach { code ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = code.name,
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = RadioCanadaRegular
                                    )
                                },
                                onClick = {
                                    anketaViewModel.updateState(
                                        stateField.copy(countryId = code.id)
                                    )
                                    expandedCountry = false
                                }
                            )
                        }
                    }
                }

                if (stateField.countryError) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Необходимо указать страну",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                var expandedActivity by remember { mutableStateOf(false) }
                val selectActivity = anketaViewModel.activitys.value?.find { it.id == stateField.activityId }

                Text(
                    text = "ОКВЭД основной",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    CustomDropDownField(
                        value = selectActivity?.name ?: "",
                        placeholder = "Выберите код",
                        onExpandedChange = { expandedActivity = it }
                    )

                    DropdownMenu(
                        expanded = expandedActivity,
                        onDismissRequest = { expandedActivity = false },
                        modifier = Modifier
                            .background(Color.White)
                            .heightIn(max = 300.dp)
                            .fillMaxWidth(),
                        offset = DpOffset(x = 0.dp, y = 0.dp)
                    ) {
                        anketaViewModel.activitys.value!!.forEach { activity ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = activity.name,
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = RadioCanadaRegular
                                    )
                                },
                                onClick = {
                                    anketaViewModel.updateState(
                                        stateField.copy(activityId = activity.id)
                                    )
                                    expandedActivity = false
                                }
                            )
                        }
                    }
                }

                if (stateField.activityError) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Необходимо указать оквэд",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular
                        )
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = "Ожидаемый месячный оборот",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextFieldNormalDigital(stateField.income) { digitsString ->
                    anketaViewModel.updateState(
                        stateField.copy(income = digitsString.toIntOrNull() ?: 0)
                    )
                }

                if (stateField.incomeError) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Необходимо указать доход",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Источник средств компании",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextFieldNormal(
                    stateField.source,
                    {
                        anketaViewModel.updateState(
                            stateField.copy(source = it)
                        )
                    },
                )
                if (stateField.sourceError) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Необходимо указать источник",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "ФИО директора",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextFieldNormal(
                    stateField.fio,
                    {
                        anketaViewModel.updateState(
                            stateField.copy(fio = it)
                        )
                    },
                )
                if (stateField.fioError) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Необходимо указать ФИО",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    when (appStateSave) {
                        is AppState.Loading -> {
                            ButtonCustom(
                                "Открыть счет",
                                false,
                                ActiveBlue,
                                NoActiveBlue,
                                16.sp,
                                14.dp,
                                150.dp,
                                45.dp
                            ) {
                                anketaViewModel.save()
                            }
                        }

                        is AppState.Initializing -> {
                            ButtonCustom(
                                "Открыть счет",
                                true,
                                ActiveBlue,
                                NoActiveBlue,
                                16.sp,
                                14.dp,
                                150.dp,
                                45.dp
                            ) {
                                anketaViewModel.save()
                            }
                        }

                        is AppState.Error -> {
                            ButtonCustom(
                                "Открыть счет",
                                true,
                                ActiveBlue,
                                NoActiveBlue,
                                16.sp,
                                14.dp,
                                150.dp,
                                45.dp
                            ) {
                                anketaViewModel.save()
                            }
                        }

                        is AppState.Success -> {
                            navController.navigate(NavRoutes.endScore)
                        }
                    }
                }
            }
        }
    }
}