package com.example.ppmob.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextFieldNormal(value: String,  onvaluechange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { onvaluechange(it) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedContainerColor = Color(0xFFE7E7E9), // цвет поля при фокусе
            unfocusedContainerColor = Color(0xFFE7E7E9), // цвет поля без фокуса
            focusedSupportingTextColor = Color.Gray,
            unfocusedSupportingTextColor = Color.Gray
        ),
    )
}