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

    // Состояние для отслеживания выбранных банков
    var selectedBankIds by remember { mutableStateOf(setOf<Int>()) }

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
            "т-банк" -> navController.navigate("tbank_screen")
            "альфа-банк" -> navController.navigate("alfa_screen")
            "втб" -> navController.navigate("vtb_screen")
            "райффайзенбанк" -> navController.navigate("raiffeisen_screen")
            "открытие" -> navController.navigate("otkritie_screen")
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
                .padding(top = 80.dp, start = 20.dp, end = 20.dp),
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
                Image(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "",
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = "Банки часто отказывают иностранцам без объяснения причин. Отправляйте анкеты в 2–3 банка одновременно — это не обязывает вас открывать счет, но экономит время при отказе",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(start = 12.dp)
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
                                onClick = {  toggleBankSelection(bank.id)},
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
                .fillMaxWidth().height(80.dp).padding(top = 7.dp),
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
                        navController.navigate(NavRoutes.menu)
                    }
                }
            }
        }
    }
}