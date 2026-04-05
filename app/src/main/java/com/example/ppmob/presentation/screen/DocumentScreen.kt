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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.viewmodel.DocumentViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.ActiveGreen
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.NoActiveGreen
import com.example.ppmob.ui.theme.RadioCanadaRegular

@Composable
fun DocumentScreen(
    navController: NavHostController,
    needApostille: Boolean,
    documentViewModel: DocumentViewModel = hiltViewModel(),
) {
    // Состояния для отслеживания нажатий кнопок
    var isApostilleCompleted by remember { mutableStateOf(false) }
    var isTranslationCompleted by remember { mutableStateOf(false) }
    var isNotaryCompleted by remember { mutableStateOf(false) }

    val imageRes = when {
        // Если апостиль нужен
        needApostille -> {
            when {
                !isApostilleCompleted -> R.drawable.passportenglish
                isApostilleCompleted && !isTranslationCompleted -> R.drawable.onlyapostil
                isTranslationCompleted && !isNotaryCompleted -> R.drawable.apostilandtranslate
                else -> R.drawable.all1
            }
        }
        // Если апостиль не нужен
        else -> {
            when {
                !isTranslationCompleted -> R.drawable.passportenglish
                isTranslationCompleted && !isNotaryCompleted -> R.drawable.passportrus
                else -> R.drawable.translateandnotarius
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 110.dp, start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "",
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // Вертикальная черта
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(35.dp)
                    .background(ActiveBlue)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = if (needApostille) {
                    "Ваша страна является участницей Гаагской Конвенции, требуется апостилирование документов"
                } else {
                    "Ваша страна не является участницей Гаагской Конвенции, требуется консульская легализация"
                },
                fontFamily = RadioCanadaRegular,
                fontSize = 13.sp,
                color = Color.Black,
                textAlign = TextAlign.Left,
                lineHeight = 14.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        ButtonCustom(
            "Апостилировать",
            needApostille && !isApostilleCompleted,
            ActiveGreen,
            NoActiveGreen,
            16.sp,
            14.dp,
            270.dp,
            45.dp
        ) {
            isApostilleCompleted = true
        }
        Spacer(modifier = Modifier.height(18.dp))

        val isTranslationEnabled = if (needApostille) {
            isApostilleCompleted && !isTranslationCompleted
        } else {
            !isTranslationCompleted
        }
        ButtonCustom(
            "Перевести на русский язык",
            isTranslationEnabled,
            ActiveGreen,
            NoActiveGreen,
            16.sp,
            14.dp,
            270.dp,
            45.dp
        ) {
            isTranslationCompleted = true
        }
        Spacer(modifier = Modifier.height(18.dp))

        val isNotaryEnabled = if (needApostille) {
            isTranslationCompleted && !isNotaryCompleted
        } else {
            isTranslationCompleted && !isNotaryCompleted
        }

        ButtonCustom(
            "Заверить перевод у нотариуса",
            isNotaryEnabled,
            ActiveGreen,
            NoActiveGreen,
            16.sp,
            14.dp,
            270.dp,
            45.dp
        ) {
            isNotaryCompleted = true
        }
        Spacer(modifier = Modifier.height(45.dp))

        val isSendEnabled = if (needApostille) {
            isApostilleCompleted && isTranslationCompleted && isNotaryCompleted
        } else {
            isTranslationCompleted && isNotaryCompleted
        }
        ButtonCustom(
            "Отправить",
            isSendEnabled,
            ActiveBlue,
            NoActiveBlue,
            16.sp,
            14.dp,
            270.dp,
            45.dp
        ) {
            navController.navigate(NavRoutes.step21)
        }
    }
}