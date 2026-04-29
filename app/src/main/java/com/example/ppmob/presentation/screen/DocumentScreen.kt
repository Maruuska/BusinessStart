package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.ActiveGreen
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.NoActiveGreen
import com.example.ppmob.ui.theme.RadioCanadaBold
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun DocumentScreen(
    navController: NavHostController,
    needApostille: Boolean,
    isSimplified: Boolean = false  // новый параметр для Беларуси
) {
    var isApostilleCompleted by remember { mutableStateOf(false) }
    var isTranslationCompleted by remember { mutableStateOf(false) }
    var isNotaryCompleted by remember { mutableStateOf(false) }

    var showInstructionDialog by remember { mutableStateOf(true) }

    val imageRes = when {
        isSimplified -> {
            when {
                !isTranslationCompleted -> R.drawable.passportenglish
                isTranslationCompleted && !isNotaryCompleted -> R.drawable.passportrus
                else -> R.drawable.translateandnotarius
            }
        }
        needApostille -> {
            when {
                !isApostilleCompleted -> R.drawable.passportenglish
                isApostilleCompleted && !isTranslationCompleted -> R.drawable.onlyapostil
                isTranslationCompleted && !isNotaryCompleted -> R.drawable.apostilandtranslate
                else -> R.drawable.all1
            }
        }
        else -> {
            when {
                !isTranslationCompleted -> R.drawable.passportenglish
                isTranslationCompleted && !isNotaryCompleted -> R.drawable.passportrus
                else -> R.drawable.translateandnotarius
            }
        }
    }

    // Текст описания в зависимости от типа
    val descriptionText = when {
        isSimplified -> "Упрощённый порядок - достаточно нотариального перевода и заверения"
        needApostille -> "Ваша страна является участницей Гаагской конвенции. Требуется апостилирование документов."
        else -> "Ваша страна не является участницей Гаагской конвенции. Требуется консульская легализация."
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Подготовка документов",
                    fontFamily = RadioCanadaSemiBold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(id = R.drawable.help),
                    contentDescription = "",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { showInstructionDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "",
                modifier = Modifier.size(280.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .fillMaxHeight()
                        .background(ActiveBlue)
                )
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = descriptionText,
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    lineHeight = 15.sp,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            if (needApostille && !isSimplified) {
                ButtonCustom(
                    "Апостилировать",
                    !isApostilleCompleted,
                    ActiveGreen,
                    NoActiveGreen,
                    16.sp,
                    14.dp,
                    270.dp,
                    45.dp
                ) {
                    isApostilleCompleted = true
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            val isTranslationEnabled = when {
                isSimplified -> !isTranslationCompleted
                needApostille -> isApostilleCompleted && !isTranslationCompleted
                else -> !isTranslationCompleted
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
            Spacer(modifier = Modifier.height(12.dp))

            val isNotaryEnabled = when {
                isSimplified -> isTranslationCompleted && !isNotaryCompleted
                needApostille -> isTranslationCompleted && !isNotaryCompleted
                else -> isTranslationCompleted && !isNotaryCompleted
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
            Spacer(modifier = Modifier.height(32.dp))

            val isSendEnabled = when {
                isSimplified -> isTranslationCompleted && isNotaryCompleted
                needApostille -> isApostilleCompleted && isTranslationCompleted && isNotaryCompleted
                else -> isTranslationCompleted && isNotaryCompleted
            }
            ButtonCustom(
                "Отправить документы",
                isSendEnabled,
                ActiveBlue,
                NoActiveBlue,
                16.sp,
                14.dp,
                270.dp,
                45.dp
            ) {
                navController.navigate(NavRoutes.step3)
            }
        }

        // окно инструкции
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
                        .padding(horizontal = 24.dp)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Инструкция",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Это демонстрационный пример подготовки документов.",
                        fontFamily = RadioCanadaBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Что нужно сделать:",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    val stepsText = when {
                        isSimplified -> "1. Нажимайте на кнопки последовательно - каждая станет доступна после выполнения предыдущего шага.\n\n" +
                                "2. Упрощённый порядок для Беларуси:\n" +
                                "   • Перевод на русский язык\n" +
                                "   • Заверка перевода у нотариуса\n" +
                                "   • В конце - отправка документов"
                        needApostille -> "1. Нажимайте на кнопки последовательно - каждая станет доступна после выполнения предыдущего шага.\n\n" +
                                "2. Порядок действий зависит от выбранной на предыдущем экране страны:\n" +
                                "   🔹 Требуется АПОСТИЛЬ\n" +
                                "   • Затем перевод на русский язык\n" +
                                "   • Затем заверка перевода у нотариуса\n" +
                                "   • В конце - отправка документов"
                        else -> "1. Нажимайте на кнопки последовательно - каждая станет доступна после выполнения предыдущего шага.\n\n" +
                                "2. Порядок действий зависит от выбранной на предыдущем экране страны:\n" +
                                "   🔸 Требуется КОНСУЛЬСКАЯ ЛЕГАЛИЗАЦИЯ\n" +
                                "   • Затем перевод на русский язык\n" +
                                "   • Затем заверка перевода у нотариуса\n" +
                                "   • В конце - отправка документов"
                    }

                    Text(
                        text = stepsText,
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Left,
                        lineHeight = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    when {
                        isSimplified -> {
                            Text(
                                text = "Что такое упрощённый порядок?",
                                fontFamily = RadioCanadaSemiBold,
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Для граждан Беларуси действует упрощённый порядок в рамках Союзного государства. Достаточно нотариально заверенного перевода документов на русский язык. Апостиль и консульская легализация не требуются.",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 12.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Left,
                                lineHeight = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        needApostille -> {
                            Text(
                                text = "Что такое апостиль?",
                                fontFamily = RadioCanadaSemiBold,
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Апостиль — это специальный штамп, который подтверждает подлинность подписи и печати на официальном документе для его использования за границей. Упрощает признание документов в странах-участницах Гаагской конвенции 1961 года.",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 12.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Left,
                                lineHeight = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        else -> {
                            Text(
                                text = "Что такое консульская легализация?",
                                fontFamily = RadioCanadaSemiBold,
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Консульская легализация — это процедура подтверждения подлинности документов в странах, не участвующих в Гаагской конвенции. Требует последовательного заверения в нескольких инстанциях: нотариус → Минюст → Консульство страны назначения. Более длительная и сложная процедура, чем апостиль.",
                                fontFamily = RadioCanadaRegular,
                                fontSize = 12.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Left,
                                lineHeight = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    ButtonCustom(
                        "Понятно",
                        true,
                        ActiveBlue,
                        NoActiveBlue,
                        14.sp,
                        12.dp,
                        200.dp,
                        42.dp
                    ) {
                        showInstructionDialog = false
                    }
                }
            }
        }
    }
}