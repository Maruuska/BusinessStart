package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.ppmob.presentation.components.MoneyAmountTextField
import com.example.ppmob.presentation.viewmodel.BalanceViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaBold
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun BalanceScreen(
    navController: NavHostController,
    balanceViewModel: BalanceViewModel = hiltViewModel(),
) {
    val stateField by balanceViewModel.fieldsBalance.collectAsState()

    // преобразование строки с пробелами в число
    fun parseSum(sumString: String): Int {
        return sumString.replace(" ", "").filter { it.isDigit() }.toIntOrNull() ?: 0
    }

    // расчет итоговой суммы (активы - пассивы)
    val totalSum = remember(stateField.rows) {
        var assetsSum = 0
        var liabilitiesSum = 0

        stateField.rows.forEach { row ->
            val sum = parseSum(row.sum)
            when (row.type) {
                "Активы" -> assetsSum += sum
                "Пассивы" -> liabilitiesSum += sum
            }
        }
        assetsSum - liabilitiesSum
    }

    // проверка на банкротство
    val isBankrot = totalSum < 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 70.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Промежуточный ",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "ликвидационный баланс",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(35.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(30.dp)
                        .background(ActiveBlue)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Промежуточный баланс — сравнение того, что компания имеет (активы) и кому должна (пассивы).",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 12.sp,
                    color = Color(0xFF696969),
                    textAlign = TextAlign.Left,
                    lineHeight = 14.sp,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Проверьте автоматически подставленные суммы",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 15.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(start = 12.dp),
                    lineHeight = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(35.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Раздел",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(0.8f), // Соответствует весу в BalanceRowItem
                    textAlign = TextAlign.Center,
                    maxLines = 1 // Запрещаем перенос
                )
                Text(
                    text = "Наименование",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1.5f), // Соответствует весу в BalanceRowItem
                    textAlign = TextAlign.Center,
                    maxLines = 1 // Запрещаем перенос
                )
                Text(
                    text = "Сумма (₽)",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    maxLines = 1 // Запрещаем перенос
                )
            }

            // Строка 1 - Денежные средства на счетах
            BalanceRowItem(
                type = stateField.rows[0].type,
                name = stateField.rows[0].name,
                sum = stateField.rows[0].sum,
                onSumChange = { newSum ->
                    balanceViewModel.updateRowSum(0, newSum)
                }
            )
            Spacer(modifier = Modifier.height(9.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)

            // Строка 2
            Spacer(modifier = Modifier.height(9.dp))
            BalanceRowItem(
                type = stateField.rows[1].type,
                name = stateField.rows[1].name,
                sum = stateField.rows[1].sum,
                onSumChange = { newSum ->
                    balanceViewModel.updateRowSum(1, newSum)
                }
            )
            Spacer(modifier = Modifier.height(9.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)

            // Строка 3
            Spacer(modifier = Modifier.height(9.dp))
            BalanceRowItem(
                type = stateField.rows[2].type,
                name = stateField.rows[2].name,
                sum = stateField.rows[2].sum,
                onSumChange = { newSum ->
                    balanceViewModel.updateRowSum(2, newSum)
                }
            )
            Spacer(modifier = Modifier.height(9.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Итого чистых активов:",
                    fontFamily = RadioCanadaBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = totalSum.toString(),
                    fontFamily = RadioCanadaRegular,
                    fontSize = 14.sp,
                    color = if (totalSum >= 0) Color.Black else Color.Red,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "₽",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 14.sp,
                    color = Color.Black,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (isBankrot) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Имущества недостаточно для покрытия долгов.",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = RadioCanadaRegular,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                    Text(
                        text = "Возможно банкротство",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = RadioCanadaRegular,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            ButtonCustom(
                label = "Далее",
                enbl = true,
                activeColor = ActiveBlue,
                noActiveColor = NoActiveBlue,
                fontSize = 16.sp,
                rounded = 14.dp,
                buttonWidth = 200.dp,
                buttonHeight = 45.dp
            ) {
                navController.navigate(NavRoutes.staff)
            }
        }
    }
}

@Composable
fun BalanceRowItem(
    type: String,
    name: String,
    sum: String,
    onSumChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .height(35.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFE8E8E8))
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = type,
                fontFamily = RadioCanadaRegular,
                fontSize = 14.sp,
                color = Color(0xFF7B7B7B),
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = name,
            fontFamily = RadioCanadaRegular,
            fontSize = 11.sp,
            color = Color.Black,
            modifier = Modifier.weight(1.5f),
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )

        MoneyAmountTextField(
            value = sum,
            onValueChange = onSumChange
        )
    }
}