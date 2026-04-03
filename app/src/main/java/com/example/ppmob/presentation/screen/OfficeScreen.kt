package com.example.ppmob.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.OutlinedTextFieldNormal
import com.example.ppmob.presentation.viewmodel.OfficeViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun OfficeScreen(
    navController: NavHostController,
    officeViewModel: OfficeViewModel = hiltViewModel()
) {

    val stateField by officeViewModel.fieldsOffice.collectAsState()
    stateField.activityId = 1
    val appState by officeViewModel.appState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Офис учредителя",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 18.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Наименование компании",
            fontFamily = RadioCanadaRegular,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextFieldNormal(stateField.name) {
            officeViewModel.updateState(
                stateField.copy(name = it)
            )
        }

        Text(
            text = "Сокращенное наименование компании",
            fontFamily = RadioCanadaRegular,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextFieldNormal(stateField.shortName) {
            officeViewModel.updateState(
                stateField.copy(shortName = it)
            )
        }

        Text(
            text = "Юридический адрес",
            fontFamily = RadioCanadaRegular,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))

//        var expanded by remember { mutableStateOf(false) }       //развернутость списка
//        val selectCategory =
//            viewModel.categories.value?.find { it.id == plantState.categoryId }
//
//        Column {
//            // текстовое поле с выпадающим списком и отображением выбранной категории
//            OutlinedTextFieldDropDown(
//                selectCategory?.categoryName ?: "Select a category"
//            ) {
//                expanded = it
//            }
//            // выпадающее меню
//            DropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false }) {
//                // перебор списка категорий из viewModel
//                viewModel.categories.value!!.forEach { categoryPlant ->
//                    DropdownMenuItem(
//                        text = { Text(categoryPlant.categoryName) },  // отображение имени категории
//                        onClick = {
//                            plantState.categoryId =
//                                categoryPlant.id   // при нажатии устанавливается выбранная категория
//                            expanded = false
//                        }
//                    )
//                }
//            }
//        }

        Text(
            text = "Вид деятельности",
            fontFamily = RadioCanadaRegular,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))


        Text(
            text = "Количество учредителей",
            fontFamily = RadioCanadaRegular,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))



        Spacer(modifier = Modifier.height(30.dp))
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
            navController.navigate(NavRoutes.office)
        }

    }
}