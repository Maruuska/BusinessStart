package com.example.ppmob.presentation.screen

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.ButtonCustomOutline
import com.example.ppmob.presentation.components.OutlinedTextFieldDropDown
import com.example.ppmob.presentation.components.OutlinedTextFieldNormal
import com.example.ppmob.presentation.viewmodel.StatementViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun StatementScreen(
    navController: NavHostController,
    statementViewModel: StatementViewModel = hiltViewModel(),
) {
    val stateField by statementViewModel.fields.collectAsState()
    val appStateSave by statementViewModel.appStateSave.collectAsState()
    val context = LocalContext.current

    // Состояние для отображения сообщения о сохранении
    var saveMessage by remember { mutableStateOf<String?>(null) }

    fun checkAndSaveZaivlenie() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            saveImageToStorage2(context, R.drawable.vipiska, "zaivlenie") { message ->
                saveMessage = message
            }
        }
    }

    fun checkAndSavePoradok() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            saveImageToStorage2(context, R.drawable.spravkarezident, "poradok") { message ->
                saveMessage = message
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 70.dp, start = 25.dp, end = 25.dp, bottom = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Заполнение заявления по форме № 11БС-Учет",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(40.dp))

        LazyColumn(

        ) {
            item {
                Text(
                    text = "Полное наименование компании",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextFieldNormal(stateField.name) {
                    statementViewModel.updateState(
                        stateField.copy(name = it)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "(в русской транскрипции)",
                        color = Color.Gray,
                        fontSize = 9.sp,
                        fontFamily = RadioCanadaRegular,
                        textAlign = TextAlign.Center
                    )
                }

                // вывод о неправильном формате наименования
                if (stateField.errorName) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Наименование должно содержать \"Общество с ограниченной ответственностью\"",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular,
                            textAlign = TextAlign.Center,
                            lineHeight = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextFieldNormal(stateField.nameLat) {
                    statementViewModel.updateState(
                        stateField.copy(nameLat = it)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "(в латинской транскрипции)",
                        color = Color.Gray,
                        fontSize = 9.sp,
                        fontFamily = RadioCanadaRegular,
                        textAlign = TextAlign.Center
                    )
                }

                // вывод о неправильном формате наименования
                if (stateField.errorNameLat) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Можно использовать только латинские буквы",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Сокращенное наименование компании",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextFieldNormal(
                    stateField.shortName,
                    {
                        statementViewModel.updateState(
                            stateField.copy(shortName = it)
                        )
                    },
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "ФИО руководителя",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextFieldNormal(
                    stateField.fio,
                    {
                        statementViewModel.updateState(
                            stateField.copy(fio = it)
                        )
                    },
                )
                if (stateField.fioError) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Необходимо указать ФИО",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                var expandedCode by remember { mutableStateOf(false) }
                val selectCode =
                    statementViewModel.codeCountries.value?.find { it.id == stateField.codeCountryId }

                Text(
                    text = "Код страны регистрации",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    OutlinedTextFieldDropDown(
                        selectCode?.code ?: "Выберите код"
                    ) {
                        expandedCode = it
                    }
                    // выпадающее меню
                    DropdownMenu(
                        expanded = expandedCode,
                        onDismissRequest = { expandedCode = false }) {
                        // перебор списка кодов из viewModel
                        statementViewModel.codeCountries.value!!.forEach { code ->
                            DropdownMenuItem(
                                text = { Text(code.code) },
                                onClick = {
                                    statementViewModel.updateState(
                                        stateField.copy(codeCountryId = code.id)
                                    )
                                    expandedCode = false
                                }
                            )
                        }
                    }
                }

                if (stateField.codeError) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Необходимо указать код",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                var expandedForm by remember { mutableStateOf(false) }
                val selectForm = statementViewModel.forms.value?.find { it.id == stateField.formId }

                Text(
                    text = "Организационно-правовая форма",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    OutlinedTextFieldDropDown(
                        selectForm?.name ?: "Выберите форму"
                    ) {
                        expandedForm = it
                    }
                    // выпадающее меню
                    DropdownMenu(
                        expanded = expandedForm,
                        onDismissRequest = { expandedForm = false }) {
                        // перебор списка форм из viewModel
                        statementViewModel.forms.value!!.forEach { form ->
                            DropdownMenuItem(
                                text = { Text(form.name) },
                                onClick = {
                                    statementViewModel.updateState(
                                        stateField.copy(formId = form.id)
                                    )
                                    expandedForm = false
                                }
                            )
                        }
                    }
                }

                if (stateField.formError) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Необходимо указать форму",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = RadioCanadaRegular
                        )
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))



                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    when (appStateSave) {
                        is AppState.Loading -> {
                            ButtonCustom(
                                "Далее",
                                false,
                                ActiveBlue,
                                NoActiveBlue,
                                16.sp,
                                14.dp,
                                130.dp,
                                45.dp
                            ) {
                                statementViewModel.save()
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
                                130.dp,
                                45.dp
                            ) {
                                statementViewModel.save()
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
                                130.dp,
                                45.dp
                            ) {
                                statementViewModel.save()
                            }
                        }

                        is AppState.Success -> {
                            navController.navigate(NavRoutes.packageStatement)
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .height(50.dp)
                        .background(Color.Transparent)
                ) { }
            }
        }
    }
}

// Функция сохранения изображения с параметром drawableResId и именем файла
fun saveImageToStorage2(context: Context, drawableResId: Int, fileNamePrefix: String, onResult: (String) -> Unit) {
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
                            "zaivlenie" -> "Заявление сохранено в папку Downloads"
                            "poradok" -> "Порядок заполнения сохранен в папку Downloads"
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