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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.OutlinedTextFieldDropDown
import com.example.ppmob.presentation.viewmodel.OfficeViewModel
import com.example.ppmob.presentation.viewmodel.PalataViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun PalataScreen(
    navController: NavHostController,
    palataViewModel: PalataViewModel = hiltViewModel(),
) {

    val stateField by palataViewModel.fieldsPalata.collectAsState()
    val appState by palataViewModel.appState.collectAsState()
    val appStateSave by palataViewModel.appStateSave.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 70.dp, start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Нотариальная палата",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(40.dp))

        LazyColumn(
            modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                when (appState) {
                    is AppState.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is AppState.Initializing -> {
                        //ничего
                    }

                    is AppState.Error -> {
                        Text((appState as AppState.Error).message)
                    }

                    is AppState.Success -> {
                        Text(
                            text = "Необходимые документы для регистрации:",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 17.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ok),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Разрешение на пребывание в РФ",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ok),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Патент или разрешение на ведение коммерческой деятельности в России",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left,
                                lineHeight = 14.sp,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = "Страна проживания",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 17.sp,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        var expanded by remember { mutableStateOf(false) }       //развернутость списка
                        val selectCountry =
                            palataViewModel.countries.value?.find { it.id == stateField.countryId }

                        Column {
                            OutlinedTextFieldDropDown(
                                selectCountry?.name ?: "Выберите страну"
                            ) {
                                expanded = it
                            }
                            // выпадающее меню
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }) {

                                palataViewModel.countries.value!!.forEach { country ->
                                    DropdownMenuItem(
                                        text = { Text(country.name) },
                                        onClick = {
                                            palataViewModel.updateState(
                                                stateField.copy(countryId = country.id)
                                            )
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        if (stateField.errorCountry) {
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

                        Text(
                            text = "Резиденту необходимо предоставить:",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 17.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ok),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Копию паспорта с переводом на русский язык",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ok),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Документ о подтверждении проживания за границей",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left,
                                lineHeight = 14.sp,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        Image(
                            painter = painterResource(id = R.drawable.table),
                            contentDescription = "",
                            modifier = Modifier.size(300.dp)
                        )

                        when (appStateSave) {
                            is AppState.Loading -> {
                                ButtonCustom(
                                    "Предоставить документы",
                                    false,
                                    ActiveBlue,
                                    NoActiveBlue,
                                    16.sp,
                                    14.dp,
                                    270.dp,
                                    45.dp
                                ) {
                                    palataViewModel.transition()
                                }
                            }

                            is AppState.Initializing -> {
                                ButtonCustom(
                                    "Предоставить документы",
                                    true,
                                    ActiveBlue,
                                    NoActiveBlue,
                                    16.sp,
                                    14.dp,
                                    270.dp,
                                    45.dp
                                ) {
                                    palataViewModel.transition(navController)
                                }
                            }

                            is AppState.Error -> {
                                ButtonCustom(
                                    "Предоставить документы",
                                    true,
                                    ActiveBlue,
                                    NoActiveBlue,
                                    16.sp,
                                    14.dp,
                                    270.dp,
                                    45.dp
                                ) {
                                    palataViewModel.transition(navController)
                                }
                            }

                            is AppState.Success -> {
                                //navController.navigate(NavRoutes.step21)
                            }
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