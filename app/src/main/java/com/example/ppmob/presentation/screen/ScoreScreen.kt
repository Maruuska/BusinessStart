package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.ppmob.domain.state.ScoreState
import com.example.ppmob.presentation.components.BankCard
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.viewmodel.ScoreViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun ScoreScreen(
    navController: NavHostController,
    scoreViewModel: ScoreViewModel = hiltViewModel(),
) {
    val appState by scoreViewModel.appState.collectAsState()
    val appStateSave by scoreViewModel.appStateSave.collectAsState()
    val banks by scoreViewModel.banks
    val scoreState by scoreViewModel.scoreState.collectAsState()

    // Состояние для отслеживания выбранных банков
    var selectedBankIds by remember { mutableStateOf(setOf<Int>()) }

    LaunchedEffect(scoreState.selectedBankIds) {
        selectedBankIds = scoreState.selectedBankIds
    }

    // Функция для выбора/отмены выбора банка
    fun toggleBankSelection(bankId: Int) {
        selectedBankIds = if (selectedBankIds.contains(bankId)) {
            selectedBankIds - bankId
        } else {
            selectedBankIds + bankId
        }
    }

    // Функция для перехода на экран деталей банка
    fun navigateToBankDetail(bankName: String) {
        when (bankName.lowercase()) {
            "сбербанк" -> navController.navigate(NavRoutes.sberScreen)
            "т-банк" -> navController.navigate(NavRoutes.tbankScreen)
            "альфа-банк" -> navController.navigate(NavRoutes.alfaScreen)
            "втб" -> navController.navigate(NavRoutes.vtbScreen)
            "райффайзенбанк" -> navController.navigate(NavRoutes.raiffeisenScreen)
            "открытие" -> navController.navigate(NavRoutes.otkritieScreen)
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
                .padding(top = 60.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Открытие счета в банке",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Нажмите на карточку банка, чтобы выбрать его — вы сможете отправить заявку сразу в несколько банков\n\n" +
                            "Нажмите «Подробнее» на карточке, чтобы узнать, какие документы нужны для открытия счета в этом банке",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color(0xFF555555),
                    textAlign = TextAlign.Left,
                    lineHeight = 18.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (appState) {
                AppState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                AppState.Initializing -> {
                    //ничего
                }

                AppState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(banks) { bank ->
                            BankCard(
                                bank = bank,
                                onClick = {
                                    toggleBankSelection(bank.id)
                                    scoreViewModel.updateState(
                                        scoreState.copy(selectedBankIds = selectedBankIds)
                                    )
                                },
                                isSelected = selectedBankIds.contains(bank.id),
                                onDetailClick = { navigateToBankDetail(bank.name) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp) // Фиксированная высота
                            )
                        }
                    }
                }

                is AppState.Error -> {
                    Text((appState as AppState.Error).message)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(top = 7.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                selectedBankIds.isEmpty() -> {
                    ButtonCustom(
                        "Далее",
                        false,
                        ActiveBlue,
                        NoActiveBlue,
                        16.sp,
                        14.dp,
                        270.dp,
                        45.dp
                    ) { }
                    Text(
                        text = "выберите хотя бы 1 банк",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 14.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                    )
                }

                appStateSave is AppState.Loading -> {
                    ButtonCustom(
                        "Далее",
                        false,
                        ActiveBlue,
                        NoActiveBlue,
                        16.sp,
                        14.dp,
                        270.dp,
                        45.dp
                    ) { }
                }

                else -> {
                    ButtonCustom(
                        "Далее",
                        true,
                        ActiveBlue,
                        NoActiveBlue,
                        16.sp,
                        14.dp,
                        270.dp,
                        45.dp
                    ) {
                        navController.navigate(NavRoutes.anketa)
                    }
                }
            }
        }
    }
}