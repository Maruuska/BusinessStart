package com.example.ppmob.presentation.screen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.OutlinedTextFieldNormal
import com.example.ppmob.presentation.viewmodel.DutiesViewModel
import com.example.ppmob.presentation.viewmodel.RightsViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaBold
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular

@SuppressLint("MutableCollectionMutableState")
@Composable
fun DutiesScreen(
    navController: NavHostController,
    dutiesViewModel: DutiesViewModel=hiltViewModel(),
) {

    val appState by dutiesViewModel.appState.collectAsState()
    val appStateSave by dutiesViewModel.appStateSave.collectAsState()
    val duties = dutiesViewModel.duties.value ?: emptyList()

    // Состояние для отслеживания выбранных прав
    var selectedRights by remember { mutableStateOf(mutableSetOf<Int>()) }

    // Состояние для диалога
    var showDialog by remember { mutableStateOf(false) }
    var newDutyName by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 80.dp, start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.Start,
    ) {

        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    navController.popBackStack()  // возврат назад без пересоздания экрана
                }
        )
        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Дополнительные",
                    fontFamily = RadioCanadaBold,
                    fontSize = 17.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    lineHeight = 14.sp,
                )
                Text(
                    text = "обязанности участников:",
                    fontFamily = RadioCanadaBold,
                    fontSize = 17.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                )
            }

            ButtonCustom(
                "Создать",
                true,
                ActiveBlue,
                NoActiveBlue,
                12.sp,
                16.dp,
                80.dp,
                35.dp
            ) {
                showDialog = true
                newDutyName = ""
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        when (appState) {
            is AppState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is AppState.Error -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (appState as AppState.Error).message,
                        color = Color.Red,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            is AppState.Success, AppState.Initializing -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(290.dp)
                )  {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(duties) { duties ->

                            val isSelected = selectedRights.contains(duties.id)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = if (isSelected) R.drawable.square2 else R.drawable.square
                                    ),
                                    contentDescription = if (isSelected) "" else "",
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clickable {
                                            selectedRights = if (isSelected) {
                                                selectedRights.toMutableSet()
                                                    .apply { remove(duties.id) }
                                            } else {
                                                selectedRights.toMutableSet()
                                                    .apply { add(duties.id) }
                                            }
                                        }
                                )
                                Text(
                                    text = duties.name,
                                    fontFamily = RadioCanadaRegular,
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    textAlign = TextAlign.Left,
                                    lineHeight = 18.sp,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 12.dp)
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ButtonCustom(
                            "Добавить в устав",
                            true,
                            ActiveBlue,
                            NoActiveBlue,
                            16.sp,
                            15.dp,
                            200.dp,
                            40.dp
                        ) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                if (!isSaving) {
                    showDialog = false
                    newDutyName = ""
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = !isSaving,
                dismissOnClickOutside = !isSaving
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
                        text = "Введите обязанность участника:",
                        fontFamily = RadioCanadaMedium,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextFieldNormal(
                        value = newDutyName,
                        onvaluechange = { newDutyName = it }
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            12.dp,
                            Alignment.CenterHorizontally
                        )
                    ) {
                        ButtonCustom(
                            "Отмена",
                            !isSaving,
                            Color(0xFF89979F),
                            Color(0xFF89979F),
                            14.sp,
                            12.dp,
                            100.dp,
                            40.dp
                        ) {
                            if (!isSaving) {
                                showDialog = false
                                newDutyName = ""
                            }
                        }

                        ButtonCustom(
                            "Сохранить",
                            newDutyName.isNotEmpty() && !isSaving,
                            ActiveBlue,
                            NoActiveBlue,
                            14.sp,
                            12.dp,
                            100.dp,
                            40.dp
                        ) {
                            if (newDutyName.isNotEmpty() && !isSaving) {
                                isSaving = true
                                dutiesViewModel.addDuty(newDutyName)
                            }
                        }
                    }

                }
            }
        }
    }

    when (appStateSave) {
        is AppState.Success -> {
            if (isSaving) {
                isSaving = false
                showDialog = false
                newDutyName = ""
            }
        }

        is AppState.Error -> {
            if (isSaving) {
                isSaving = false
            }
        }

        else -> {}
    }
}