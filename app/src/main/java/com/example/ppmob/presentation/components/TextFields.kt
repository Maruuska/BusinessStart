package com.example.ppmob.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppmob.R

@Composable
fun OutlinedTextFieldNormal(value: String, onvaluechange: (String) -> Unit) {
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

@Composable
fun OutlinedTextFieldDropDown(value: String, onExpandedChange: (Boolean) -> Unit) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        onValueChange = {},
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
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        trailingIcon = {
            IconButton(onClick = { onExpandedChange(true) }) {
                Icon(
                    painter = painterResource(R.drawable.down),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        readOnly = true,
    )
}

@Composable
fun OutlinedTextFieldDigital(value: String, onvaluechange: (String) -> Unit) {

    val displayValue = remember(value) {
        if (value.isNotEmpty()) {
            val number = value.toLongOrNull()
            if (number != null) {
                "%,d ₽".format(number).replace(",", " ")
            } else {
                ""
            }
        } else {
            ""
        }
    }

    OutlinedTextField(
        value = displayValue,
        onValueChange = { newValue ->
            // Извлекаем только цифры из введённого текста
            val digitsOnly = newValue.filter { it.isDigit() }
            onvaluechange(digitsOnly)
        },
        placeholder = {
            Text(
                text = "Сколько",
                color = Color.Gray,
                fontSize = 14.sp
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color(0xFF353835),
            unfocusedContainerColor = Color(0xFF353835),
        ),
    )
}

@Composable
fun OutlinedTextFieldDigitalNoEdit(value: String, onvaluechange: (String) -> Unit) {

    val displayValue = remember(value) {
        if (value.isNotEmpty()) {
            val number = value.toLongOrNull()
            if (number != null) {
                "%,d ₽".format(number).replace(",", " ")
            } else {
                ""
            }
        } else {
            ""
        }
    }

    OutlinedTextField(
        value = displayValue,
        onValueChange = { newValue ->
            // Извлекаем только цифры из введённого текста
            val digitsOnly = newValue.filter { it.isDigit() }
            onvaluechange(digitsOnly)
        },
        placeholder = {
            Text(
                text = "Сколько",
                color = Color.Gray,
                fontSize = 14.sp
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color(0xFF353835),
            unfocusedContainerColor = Color(0xFF353835),
            disabledBorderColor = Color.Transparent,
            disabledTextColor = Color.White,
            disabledLabelColor = Color.Gray,
            disabledPlaceholderColor = Color.Gray,
            disabledContainerColor = Color(0xFF353835),
            disabledSupportingTextColor = Color.Gray,
            disabledLeadingIconColor = Color.Gray,
            disabledTrailingIconColor = Color.Gray

        ),
        enabled = false,
    )
}