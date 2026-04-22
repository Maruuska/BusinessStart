package com.example.ppmob.presentation.screen

import android.os.Build
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.ButtonCustomOutline2
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.ActiveGreen
import com.example.ppmob.ui.theme.NoActiveGreen
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun NotifyScreen(navController: NavHostController) {

    // Состояние для отображения сообщения о сохранении
    var saveMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    fun checkAndSaveForm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            saveImageToStorage(context, R.drawable.form15016, "form15016") { message ->
                saveMessage = message
            }
        }
    }

    // Состояния для отслеживания выбранных пунктов
    var isPublishedBulletin by remember { mutableStateOf(false) }
    var isPublishedEFRS by remember { mutableStateOf(false) }
    var isNotifiedCreditors by remember { mutableStateOf(false) }

    // Проверка, выбраны ли все три пункта
    val isAllSelected = isPublishedBulletin && isPublishedEFRS && isNotifiedCreditors


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
                text = "Оповещение о ликвидации",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(45.dp))

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
                    text = "В течение трех дней с момента принятия решения о прекращении существования юрлица нужно уведомить об этом ФНС по специальной форме",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            ButtonCustomOutline2(
                "Скачать форму уведомления Р15016",
                true,
                { checkAndSaveForm() },
                200.dp,
                40.dp,
                12.sp,
                14.dp
            )

            saveMessage?.let { message ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    color = if (message.contains("Ошибка")) Color.Red else Color.Black,
                    fontSize = 12.sp,
                    fontFamily = RadioCanadaRegular,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            CheckboxRow(
                text = "Опубликовано в «Вестнике государственной регистрации»",
                isChecked = isPublishedBulletin,
                onCheckedChange = { isPublishedBulletin = it }
            )
            Spacer(modifier = Modifier.height(13.dp))


            CheckboxRow(
                text = "Опубликовано в ЕФРСФДЮЛ",
                isChecked = isPublishedEFRS,
                onCheckedChange = { isPublishedEFRS = it }
            )
            Spacer(modifier = Modifier.height(12.dp))

            CheckboxRow(
                text = "Уведомления кредиторам отправлены",
                isChecked = isNotifiedCreditors,
                onCheckedChange = { isNotifiedCreditors = it }
            )
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(33.dp)
                        .background(ActiveBlue)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Публикации обязательны. Пропуск этого этапа — причина для отказа ФНС",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 12.sp,
                    color = Color(0xFF696969),
                    textAlign = TextAlign.Left,
                    lineHeight = 16.sp,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            ButtonCustom(
                label = "Далее",
                enbl = isAllSelected,
                activeColor = ActiveGreen,
                noActiveColor = NoActiveGreen,
                fontSize = 16.sp,
                rounded = 14.dp,
                buttonWidth = 200.dp,
                buttonHeight = 45.dp
            ) {
                navController.navigate(NavRoutes.debit)
            }
        }
    }
}

@Composable
fun CheckboxRow(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(
                id = if (isChecked) R.drawable.square2 else R.drawable.square
            ),
            contentDescription = "",
            modifier = Modifier
                .size(22.dp)
                .clickable { onCheckedChange(!isChecked) }
        )
        Text(
            text = text,
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