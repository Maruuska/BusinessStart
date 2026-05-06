package com.example.ppmob.presentation.screen

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
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
import com.example.ppmob.presentation.components.ButtonCustomOutline
import com.example.ppmob.presentation.components.CustomDropDownField
import com.example.ppmob.presentation.components.OutlinedTextFieldDropDown
import com.example.ppmob.presentation.viewmodel.AccountingViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaBold
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AccountingScreen(
    navController: NavHostController,
    accountingViewModel: AccountingViewModel = hiltViewModel()
) {
    val stateField by accountingViewModel.fields.collectAsState()
    val appState by accountingViewModel.appState.collectAsState()
    val appStateSave by accountingViewModel.appStateSave.collectAsState()
    val context = LocalContext.current

    // Состояние для отображения сообщения о сохранении
    var saveMessage by remember { mutableStateOf<String?>(null) }

    // Состояние для диалога с инструкцией
    var showInstructionDialog by remember { mutableStateOf(true) }

    fun checkAndSaveVipiska() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            saveImageToStorage(context, R.drawable.vipiska, "vipiska") { message ->
                saveMessage = message
            }
        }
    }

    fun checkAndSaveSpravka() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            saveImageToStorage(context, R.drawable.spravkarezident, "spravkarezident") { message ->
                saveMessage = message
            }
        }
    }

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
                text = "Запрос и проверка документов",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                painter = painterResource(id = R.drawable.help),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        showInstructionDialog = true
                    }
            )
        }
        Spacer(modifier = Modifier.height(35.dp))

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
                Text(
                    text = "Документы для постановки на учёт:",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ok),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Выписка из торгового реестра",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))

                Row(
                    modifier = Modifier
                        .padding(start = 27.dp)
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
                    Spacer(modifier = Modifier.width(9.dp))
                    Text(
                        text = "Или иной документ, имеющий равную юридическую силу, подтверждающий легальный статус иностранной компании в стране регистрации",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 12.sp,
                        color = Color(0xFF696969),
                        textAlign = TextAlign.Left,
                        lineHeight = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ok),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Документ о регистрации в качестве налогоплательщика",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        lineHeight = 14.sp,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))

                Row(
                    modifier = Modifier
                        .padding(start = 27.dp)
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
                    Spacer(modifier = Modifier.width(9.dp))
                    Text(
                        text = "Если в выписке из торгового реестра уже указан налоговый код (TIN, EIN, VAT ID), этот документ НЕ требуется. В иных случаях — сертификат резидентства или справка из налоговой службы страны регистрации",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 12.sp,
                        color = Color(0xFF696969),
                        textAlign = TextAlign.Left,
                        lineHeight = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))
                Text(
                    text = "Страна регистрации иностранного учредителя",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                var expanded by remember { mutableStateOf(false) }
                val selectCountry =
                    accountingViewModel.countries.value?.find { it.id == stateField.countryId }

                Column(modifier = Modifier.fillMaxWidth()) {
                    CustomDropDownField(
                        value = selectCountry?.name ?: "",
                        placeholder = "Выберите страну",
                        onExpandedChange = { expanded = it }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(Color.White)
                            .heightIn(max = 300.dp)
                            .fillMaxWidth(),
                        offset = DpOffset(x = 0.dp, y = 0.dp)
                    ) {
                        accountingViewModel.countries.value?.forEach { country ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = country.name,
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = RadioCanadaRegular
                                    )
                                },
                                onClick = {
                                    accountingViewModel.updateState(
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
                            text = "Выберите страну",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (selectCountry?.taxCode == true) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.info),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Налоговый код уже указан в выписке. Отдельный документ о налоговой регистрации не требуется.",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color(0xFF2E7D32),
                            textAlign = TextAlign.Left,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ButtonCustomOutline(
                        "Скачать образец выписки",
                        true,
                        { checkAndSaveVipiska() },
                        Modifier.weight(1f),
                        40.dp,
                        12.sp,
                        18.dp
                    )

                    ButtonCustomOutline(
                        "Скачать образец сертификата",
                        true,
                        { checkAndSaveSpravka() },
                        Modifier.weight(1f),
                        40.dp,
                        12.sp,
                        18.dp
                    )
                }

                saveMessage?.let { message ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = message,
                        color = if (message.contains("Ошибка") || message.contains("ошибка")) Color.Red else Color(0xFF2E7D32),
                        fontSize = 12.sp,
                        fontFamily = RadioCanadaRegular,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(45.dp))
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
                            accountingViewModel.transition()
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
                            270.dp,
                            45.dp
                        ) {
                            accountingViewModel.transition()
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
                            270.dp,
                            45.dp
                        ) {
                            accountingViewModel.transition()
                        }
                    }

                    is AppState.Success -> {
                        navController.navigate(NavRoutes.apostil)
                    }
                }
            }
        }
    }

    // Диалог с инструкцией
    if (showInstructionDialog) {
        Dialog(
            onDismissRequest = { showInstructionDialog = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
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
                        text = "Инструкция",
                        fontFamily = RadioCanadaBold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Что нужно сделать?",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Укажите страну, в которой зарегистрирован иностранный учредитель (участник) вашей компании.\n",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 12.sp,
                        color = Color(0xFF555555),
                        lineHeight = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Что такое налоговый код?",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Это уникальный идентификатор налогоплательщика в стране регистрации компании\n" ,
                        fontFamily = RadioCanadaRegular,
                        fontSize = 12.sp,
                        color = Color(0xFF555555),
                        lineHeight = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Дополнительно:",
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• Вы можете скачать образцы документов (выписка из реестра и сертификат резидента)\n" +
                                "• Файлы сохранятся в папку «Загрузки» на вашем устройстве\n" +
                                "• Это поможет вам понять, как должны выглядеть ваши документы",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 12.sp,
                        color = Color(0xFF555555),
                        lineHeight = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    ButtonCustom(
                        "Понятно",
                        true,
                        ActiveBlue,
                        NoActiveBlue,
                        14.sp,
                        12.dp,
                        140.dp,
                        40.dp
                    ) {
                        showInstructionDialog = false
                    }
                }
            }
        }
    }
}

fun saveImageToStorage(context: Context, drawableResId: Int, fileNamePrefix: String, onResult: (String) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val bitmap = BitmapFactory.decodeResource(context.resources, drawableResId)

            if (bitmap == null) {
                withContext(Dispatchers.Main) {
                    onResult("Ошибка: не удалось загрузить изображение")
                }
                return@launch
            }

            val fileName = "${fileNamePrefix}_${System.currentTimeMillis()}.jpg"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = context.contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }

                val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                uri?.let {
                    resolver.openOutputStream(it)?.use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                    withContext(Dispatchers.Main) {
                        val message = when (fileNamePrefix) {
                            "vipiska" -> "✓ Выписка сохранена в папку Загрузки"
                            "spravkarezident" -> "✓ Сертификат сохранен в папку Загрузки"
                            else -> "✓ Файл сохранен в папку Загрузки"
                        }
                        onResult(message)
                    }
                } ?: run {
                    withContext(Dispatchers.Main) {
                        onResult("Ошибка при сохранении файла")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                onResult("Ошибка при сохранении: ${e.message}")
            }
        }
    }
}