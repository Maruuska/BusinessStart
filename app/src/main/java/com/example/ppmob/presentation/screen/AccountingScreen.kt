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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ppmob.R
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.ButtonCustomOutline
import com.example.ppmob.presentation.components.OutlinedTextFieldDropDown
import com.example.ppmob.presentation.viewmodel.AccountingViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
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
            .padding(top = 100.dp, start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Запрос и проверка документов",
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
                Text(
                    text = "Необходимые документы:",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 17.sp,
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
                        fontSize = 15.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))

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
                        text = "Или иной документ, имеющий равную юридическую силу, подтверждающий юридический статус иностранной компании",
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
                        fontSize = 15.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        lineHeight = 14.sp,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))

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
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "Если в выписке из торгового реестра уже указан налоговый код (TIN, EIN, VAT ID), этот документ НЕ требуется. В иных случаях — сертификат резидентства или справка из налоговой страны происхождения",
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
                    text = "Страна учредителя",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 17.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                var expanded by remember { mutableStateOf(false) }
                val selectCountry =
                    accountingViewModel.countries.value?.find { it.id == stateField.countryId }

                Column {
                    OutlinedTextFieldDropDown(
                        selectCountry?.name ?: "Выберите страну"
                    ) {
                        expanded = it
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {

                        accountingViewModel.countries.value!!.forEach { country ->
                            DropdownMenuItem(
                                text = { Text(country.name) },
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
                            text = "Необходимо указать страну",
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
                            text = "Налоговый код указан в выписке, отдельный документ о регистрации не требуется",
                            fontFamily = RadioCanadaRegular,
                            fontSize = 13.sp,
                            color = Color(0xFF696969),
                            textAlign = TextAlign.Left,
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row {
                    ButtonCustomOutline(
                        "Образец выписки",
                        true,
                        { checkAndSaveVipiska() },
                        180.dp,
                        40.dp,
                        12.sp,
                        18.dp
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    ButtonCustomOutline(
                        "Образец сертификата",
                        true,
                        { checkAndSaveSpravka() },
                        200.dp,
                        40.dp,
                        12.sp,
                        18.dp
                    )
                }

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
                        //navController.navigate(NavRoutes.step21)
                    }
                }
            }
        }
    }
}

// Функция сохранения изображения с параметром drawableResId и именем файла
fun saveImageToStorage(context: Context, drawableResId: Int, fileNamePrefix: String, onResult: (String) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            // загрузка изображения из drawable
            val bitmap = BitmapFactory.decodeResource(context.resources, drawableResId)

            if (bitmap == null) {
                withContext(Dispatchers.Main) {
                    onResult("Ошибка: не удалось загрузить изображение")
                }
                return@launch
            }

            val fileName = "${fileNamePrefix}_${System.currentTimeMillis()}.jpg"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Для Android 10 и выше используем MediaStore
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
                            "vipiska" -> "Выписка сохранена в папку Downloads"
                            "spravkarezident" -> "Сертификат сохранен в папку Downloads"
                            else -> "Файл сохранен в папку Downloads"
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