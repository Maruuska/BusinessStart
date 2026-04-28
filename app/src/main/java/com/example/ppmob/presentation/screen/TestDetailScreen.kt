package com.example.ppmob.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun TestDetailScreen(navController: NavHostController, id: String) {

    Text(text = "Тест. ${id}")

}