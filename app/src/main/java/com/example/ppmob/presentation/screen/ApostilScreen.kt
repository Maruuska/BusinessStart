package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.ppmob.presentation.components.ButtonCustomOutline
import com.example.ppmob.presentation.components.OutlinedTextFieldDropDown
import com.example.ppmob.presentation.viewmodel.ApostilViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun ApostilScreen(
    navController: NavHostController,
    apostilViewModel: ApostilViewModel = hiltViewModel(),
) {
    val stateField by apostilViewModel.fields.collectAsState()
    val appState by apostilViewModel.appState.collectAsState()
    val appStateSave by apostilViewModel.appStateSave.collectAsState()

    // Состояния для чекбоксов
    var isDocumentsSent by remember { mutableStateOf(false) }
    var isDocumentsTranslated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 100.dp, start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Апостилирование или консульская легализация",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(40.dp))

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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.info),
                        contentDescription = "",
                        modifier = Modifier.size(35.dp)
                    )
                    Text(
                        text = "Данный этап может занимать больше всего времени, поэтому важно внимательно подойти к нему и предшествующему первому этапу. В случае ошибки вы потеряете до месяца на повторный процесс.",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,

                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))

                Text(
                    text = "Выберите страну регистрации компании",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 17.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                var expanded by remember { mutableStateOf(false) }
                val selectCountry =
                    apostilViewModel.countries.value?.find { it.id == stateField.countryId }

                Column {
                    OutlinedTextFieldDropDown(
                        selectCountry?.name ?: "Выберите страну"
                    ) {
                        expanded = it
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {

                        apostilViewModel.countries.value!!.forEach { country ->
                            DropdownMenuItem(
                                text = { Text(country.name) },
                                onClick = {
                                    apostilViewModel.updateState(
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

                Spacer(modifier = Modifier.height(12.dp))
                if (selectCountry?.legal == "Апостиль") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(50.dp)
                                .background(ActiveBlue)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Требуется апостиль. Проставляется в стране выдачи документа. Не требует дальнейшей консульской легализации",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 12.sp,
                            color = Color(0xFF696969),
                            textAlign = TextAlign.Left,
                            lineHeight = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                else if(selectCountry?.legal== "Консульская легализация"){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(50.dp)
                                .background(ActiveBlue)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Требуется консульская легализация:\n" +
                                    "1) заверение в МИД страны\n" +
                                    "2) заверение в консульстве РФ в стране исхода",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 12.sp,
                            color = Color(0xFF696969),
                            textAlign = TextAlign.Left,
                            lineHeight = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isDocumentsSent) R.drawable.square2 else R.drawable.square
                        ),
                        contentDescription = if (isDocumentsSent) "Выбрано" else "Не выбрано",
                        modifier = Modifier
                            .size(22.dp)
                            .clickable {
                                isDocumentsSent = !isDocumentsSent
                            }
                    )
                    Text(
                        text = "Документы направлены на апостиль/легализацию",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        lineHeight = 18.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isDocumentsTranslated) R.drawable.square2 else R.drawable.square
                        ),
                        contentDescription = if (isDocumentsTranslated) "Выбрано" else "Не выбрано",
                        modifier = Modifier
                            .size(22.dp)
                            .clickable {
                                isDocumentsTranslated = !isDocumentsTranslated
                            }
                    )
                    Text(
                        text = "Документы переведены и заверены нотариально",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        lineHeight = 18.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                when (appStateSave) {
                    is AppState.Loading -> {
                        ButtonCustom(
                            "Далее",
                            false,
                            ActiveBlue,
                            NoActiveBlue,
                            16.sp,
                            14.dp,
                            270.dp,
                            45.dp
                        ) {
                            apostilViewModel.transition()
                        }
                    }

                    is AppState.Initializing -> {
                        ButtonCustom(
                            "Далее",
                            isDocumentsSent && isDocumentsTranslated,
                            ActiveBlue,
                            NoActiveBlue,
                            16.sp,
                            14.dp,
                            270.dp,
                            45.dp
                        ) {
                            apostilViewModel.transition()
                        }
                    }

                    is AppState.Error -> {
                        ButtonCustom(
                            "Далее",
                            isDocumentsSent && isDocumentsTranslated,
                            ActiveBlue,
                            NoActiveBlue,
                            16.sp,
                            14.dp,
                            270.dp,
                            45.dp
                        ) {
                            apostilViewModel.transition()
                        }
                    }

                    is AppState.Success -> {
                        navController.navigate(NavRoutes.statement)
                    }
                }
            }
        }
    }
}