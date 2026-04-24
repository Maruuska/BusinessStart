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
import com.example.ppmob.presentation.viewmodel.RightsViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular

@SuppressLint("MutableCollectionMutableState")
@Composable
fun RightScreen(
    navController: NavHostController,
    rightsViewModel: RightsViewModel = hiltViewModel(),
) {
    val appState by rightsViewModel.appState.collectAsState()
    val appStateSave by rightsViewModel.appStateSave.collectAsState()
    val rights = rightsViewModel.rights.value ?: emptyList()

    // Состояние для отслеживания выбранных прав
    var selectedRights by remember { mutableStateOf(mutableSetOf<Int>()) }

    // Состояние для диалога
    var showDialog by remember { mutableStateOf(false) }
    var newRightName by remember { mutableStateOf("") }
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
        Text(
            text = "Дополнительные права участников:",
            fontFamily = RadioCanadaMedium,
            fontSize = 17.sp,
            color = Color.Black,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(start = 5.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(64.dp)
                    .background(ActiveBlue)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Если вы добавляете дополнительные права, они должны быть равны для всех участников или закреплены в уставе для конкретных участников. Иначе — риск оспаривания.",
                fontFamily = RadioCanadaRegular,
                fontSize = 12.sp,
                color = Color(0xFF696969),
                textAlign = TextAlign.Left,
                lineHeight = 16.sp,
                modifier = Modifier.weight(1f)
            )
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
                    modifier = Modifier.weight(1f)
                ) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(rights) { right ->

                            val isSelected = selectedRights.contains(right.id)

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
                                                    .apply { remove(right.id) }
                                            } else {
                                                selectedRights.toMutableSet()
                                                    .apply { add(right.id) }
                                            }
                                        }
                                )
                                Text(
                                    text = right.name,
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
                            .padding(top = 16.dp, bottom = 400.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ButtonCustom(
                            "Добавить",
                            true,
                            ActiveBlue,
                            NoActiveBlue,
                            16.sp,
                            15.dp,
                            110.dp,
                            40.dp
                        ) {
                            showDialog = true
                            newRightName = ""
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
                    newRightName = ""
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
                        text = "Введите право участника:",
                        fontFamily = RadioCanadaMedium,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextFieldNormal(
                        value = newRightName,
                        onvaluechange = { newRightName = it }
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
                                newRightName = ""
                            }
                        }

                        ButtonCustom(
                            "Сохранить",
                            newRightName.isNotEmpty() && !isSaving,
                            ActiveBlue,
                            NoActiveBlue,
                            14.sp,
                            12.dp,
                            100.dp,
                            40.dp
                        ) {
                            if (newRightName.isNotEmpty() && !isSaving) {
                                isSaving = true
                                rightsViewModel.addRight(newRightName)
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
                newRightName = ""
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