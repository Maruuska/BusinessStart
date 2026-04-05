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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.RadioButton
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
import com.example.ppmob.presentation.components.OutlinedTextFieldNormal
import com.example.ppmob.presentation.viewmodel.OfficeViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun OfficeScreen(
    navController: NavHostController,
    officeViewModel: OfficeViewModel = hiltViewModel(),
) {

    val stateField by officeViewModel.fieldsOffice.collectAsState()
    val appState by officeViewModel.appState.collectAsState()
    val appStateSave by officeViewModel.appStateSave.collectAsState()
    val nameError by officeViewModel.nameError.collectAsState()
    val addressError by officeViewModel.addressError.collectAsState()
    val activityError by officeViewModel.activityError.collectAsState()

    // Состояние для выбранной радиокнопки
    var selectedFounderType by remember { mutableStateOf<Boolean?>(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 100.dp, start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Офис учредителя",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 18.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(50.dp))

        when (appState) {
            is AppState.Loading -> {  //когда страница загружается
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

            is AppState.Error -> {   //ошибка загрузки страницы
                Text((appState as AppState.Error).message)
            }

            is AppState.Success -> {
                LazyColumn(

                ) {
                    item {
                        Text(
                            text = "Наименование компании",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextFieldNormal(stateField.name) {
                            officeViewModel.updateState(
                                stateField.copy(name = it)
                            )
                        }
                        // вывод о неправильном формате наименования
                        if (stateField.errorName) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = nameError ?: "",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    fontFamily = RadioCanadaRegular,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Сокращенное наименование компании",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextFieldNormal(
                            stateField.shortName,
                            {
                                officeViewModel.updateState(
                                    stateField.copy(shortName = it)
                                )
                            },
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Юридический адрес",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        var expanded by remember { mutableStateOf(false) }       //развернутость списка
                        val selectAddress =
                            officeViewModel.addresses.value?.find { it.id == stateField.addressId }

                        Column {
                            OutlinedTextFieldDropDown(
                                selectAddress?.name ?: "Выберите адрес компании"
                            ) {
                                expanded = it
                            }
                            // выпадающее меню
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }) {
                                // перебор списка адресов из viewModel
                                officeViewModel.addresses.value!!.forEach { adres ->
                                    DropdownMenuItem(
                                        text = { Text(adres.name) },
                                        onClick = {
                                            //stateField.addressId = adres.id
                                            officeViewModel.updateState(
                                                stateField.copy(addressId = adres.id)
                                            )
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                        // вывод о неправильном формате адреса
                        if (stateField.errorAddress) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = addressError ?: "",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    fontFamily = RadioCanadaRegular
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Вид деятельности",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        var expandedActivity by remember { mutableStateOf(false) }       //развернутость списка
                        val selectActivity =
                            officeViewModel.activitys.value?.find { it.id == stateField.activityId }

                        Column {
                            OutlinedTextFieldDropDown(
                                selectActivity?.name ?: "Выберите вид деятельности"
                            ) {
                                expandedActivity = it
                            }
                            // выпадающее меню
                            DropdownMenu(
                                expanded = expandedActivity,
                                onDismissRequest = { expandedActivity = false }) {
                                // перебор списка видов из viewModel
                                officeViewModel.activitys.value!!.forEach { activity ->
                                    DropdownMenuItem(
                                        text = { Text(activity.name) },
                                        onClick = {
                                            //stateField.addressId = adres.id
                                            officeViewModel.updateState(
                                                stateField.copy(activityId = activity.id)
                                            )
                                            expandedActivity = false
                                        }
                                    )
                                }
                            }
                        }
                        // вывод о неправильном формате деятельности
                        if (stateField.errorActivity) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = activityError ?: "",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    fontFamily = RadioCanadaRegular
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Количество учредителей",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                RadioButton(
                                    selected = selectedFounderType == true,
                                    onClick = {
                                        selectedFounderType = true
                                        officeViewModel.updateState(
                                            stateField.copy(oneFounder = true)
                                        )
                                    },
                                    modifier = Modifier.size(22.dp),
                                    colors = androidx.compose.material3.RadioButtonDefaults.colors(
                                        selectedColor = Color.Black,
                                        unselectedColor = Color.Black
                                    )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Один",
                                    fontFamily = RadioCanadaRegular,
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                                Spacer(modifier = Modifier.width(54.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.link),
                                    contentDescription = "",
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable {
                                            navController.navigate(
                                                NavRoutes.oneFounder
                                            )
                                        }
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                RadioButton(
                                    selected = selectedFounderType == false,
                                    onClick = {
                                        selectedFounderType = false
                                        officeViewModel.updateState(
                                            stateField.copy(oneFounder = false)
                                        )
                                    },
                                    modifier = Modifier.size(22.dp),
                                    colors = androidx.compose.material3.RadioButtonDefaults.colors(
                                        selectedColor = Color.Black,
                                        unselectedColor = Color.Black
                                    )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Несколько",
                                    fontFamily = RadioCanadaRegular,
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.link),
                                    contentDescription = "",
                                    alignment = Alignment.Center,
                                    modifier = Modifier.size(20.dp)
                                        .clickable {
                                            navController.navigate(
                                                NavRoutes.severalFounder
                                            )
                                        }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(35.dp))

                        // обработка состояний кнопки
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            when (appStateSave) {
                                is AppState.Loading -> {
                                    ButtonCustom(
                                        "Далее",
                                        false,
                                        ActiveBlue,
                                        NoActiveBlue,
                                        16.sp,
                                        14.dp,
                                        130.dp,
                                        45.dp
                                    ) {
                                        officeViewModel.save()
                                    }
                                }

                                is AppState.Initializing -> {
                                    ButtonCustom(
                                        "Далее",
                                        true,
                                        ActiveBlue,
                                        NoActiveBlue,
                                        16.sp,
                                        14.dp,
                                        130.dp,
                                        45.dp
                                    ) {
                                        officeViewModel.save()
                                    }
                                }

                                is AppState.Error -> {
                                    ButtonCustom(
                                        "Далее",
                                        true,
                                        ActiveBlue,
                                        NoActiveBlue,
                                        16.sp,
                                        14.dp,
                                        130.dp,
                                        45.dp
                                    ) {
                                        officeViewModel.save()
                                    }
                                }

                                is AppState.Success -> {
                                    navController.navigate(NavRoutes.step2)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}