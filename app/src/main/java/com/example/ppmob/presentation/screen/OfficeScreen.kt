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
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.OutlinedTextFieldDropDown
import com.example.ppmob.presentation.components.OutlinedTextFieldNormal
import com.example.ppmob.presentation.viewmodel.OfficeViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold
import com.example.ppmob.ui.theme.WarningYellow

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
    val showLicenseWarning by officeViewModel.showLicenseWarning.collectAsState()

    // Состояние для выбранной радиокнопки
    var selectedFounderType by remember { mutableStateOf<Boolean?>(true) }

    // Состояние для всплывающего окна инструкции
    var showInstructionDialog by remember { mutableStateOf(true) }

    // сохранение selectedFounderType при изменении stateField
    LaunchedEffect(stateField.oneFounder) {
        selectedFounderType = stateField.oneFounder
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 70.dp, start = 25.dp, end = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Офис учредителя",
                    fontFamily = RadioCanadaSemiBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.help),
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { showInstructionDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            when (appState) {
                is AppState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is AppState.Initializing -> {}

                is AppState.Error -> {
                    Text((appState as AppState.Error).message)
                }

                is AppState.Success -> {
                    LazyColumn {
                        item {
                            Text(
                                text = "Придумайте наименование компании",
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
                                text = "Укажите сокращенное наименование компании",
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
                                text = "Выберите юридический адрес компании",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            var expanded by remember { mutableStateOf(false) }
                            val selectAddress =
                                officeViewModel.addresses.value?.find { it.id == stateField.addressId }

                            Column {
                                OutlinedTextFieldDropDown(
                                    selectAddress?.name ?: "Выберите адрес компании"
                                ) {
                                    expanded = it
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }) {
                                    officeViewModel.addresses.value!!.forEach { adres ->
                                        DropdownMenuItem(
                                            text = { Text(adres.name) },
                                            onClick = {
                                                officeViewModel.updateState(
                                                    stateField.copy(addressId = adres.id)
                                                )
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
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
                                text = "Выберите вид деятельности компании",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            var expandedActivity by remember { mutableStateOf(false) }
                            val selectActivity =
                                officeViewModel.activitys.value?.find { it.id == stateField.activityId }

                            Column {
                                OutlinedTextFieldDropDown(
                                    selectActivity?.name ?: "Выберите вид деятельности"
                                ) {
                                    expandedActivity = it
                                }
                                DropdownMenu(
                                    expanded = expandedActivity,
                                    onDismissRequest = { expandedActivity = false }) {
                                    officeViewModel.activitys.value!!.forEach { activity ->
                                        DropdownMenuItem(
                                            text = { Text(activity.name) },
                                            onClick = {
                                                officeViewModel.updateState(
                                                    stateField.copy(activityId = activity.id)
                                                )
                                                expandedActivity = false
                                            }
                                        )
                                    }
                                }

                                if (showLicenseWarning) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.warning),
                                            contentDescription = "",
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Для данного вида деятельности потребуется лицензия",
                                            color = WarningYellow,
                                            fontSize = 12.sp,
                                            fontFamily = RadioCanadaRegular,
                                            textAlign = TextAlign.Center,
                                            lineHeight = 14.sp
                                        )
                                    }
                                }
                            }
 
                            if (stateField.errorActivity) {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = activityError ?: "",
                                        color = Color.Red,
                                        fontSize = 12.sp,
                                        fontFamily = RadioCanadaRegular,
                                        lineHeight = 12.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Определите количество учредителей",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "(нажмите на иконку справа для подробной информации)",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 11.sp,
                                color = Color.Gray,
                                modifier = Modifier.fillMaxWidth(),
                                lineHeight = 12.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(45.dp),
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
                                    Text(
                                        text = "Один учредитель",
                                        fontFamily = RadioCanadaRegular,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        modifier = Modifier.padding(start = 12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.info),
                                        contentDescription = "Подробнее об одном учредителе",
                                        alignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable {
                                                navController.navigate(NavRoutes.oneFounder)
                                            }
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(45.dp),
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
                                    Text(
                                        text = "Несколько учредителей",
                                        fontFamily = RadioCanadaRegular,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        modifier = Modifier.padding(start = 12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.info),
                                        contentDescription = "Подробнее о нескольких учредителях",
                                        alignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable {
                                                navController.navigate(NavRoutes.severalFounder)
                                            }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(35.dp))

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
                            Column(modifier = Modifier.height(50.dp).background(Color.Transparent)) { }
                        }
                    }
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
                        .padding(horizontal = 30.dp)
                        .background(
                            Color.White,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
                        )
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Инструкция по заполнению",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "1. Определитесь с наименованием, адресом офиса и видами деятельности ООО, а также исполнительным органом.",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 14.sp,
                        color = Color.Black,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "2. В рамках имитации регистрации корректно обрабатываются только те адреса из списка, которые содержат в своём описании признак конкретного рабочего помещения (например, указание на офис, этаж, бизнес-центр с номером помещения). ",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 14.sp,
                        color = Color.Black,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "В данном случае перед вами тестовые данные, которые используются только для имитации процесса регистрации в демонстрационных целях.",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(28.dp))

                    ButtonCustom(
                        "Ознакомлен",
                        true,
                        ActiveBlue,
                        NoActiveBlue,
                        16.sp,
                        14.dp,
                        180.dp,
                        45.dp
                    ) {
                        showInstructionDialog = false
                    }
                }
            }
        }
    }
}