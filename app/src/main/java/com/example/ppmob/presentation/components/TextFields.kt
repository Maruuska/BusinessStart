package com.example.ppmob.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppmob.R
import com.example.ppmob.ui.theme.RadioCanadaRegular

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
fun OutlinedTextFieldEmail(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "example@email.com",
                fontFamily = RadioCanadaRegular,
                color = Color.Gray
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        shape = RoundedCornerShape(17.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedContainerColor = Color(0xFFE7E7E9),
            unfocusedContainerColor = Color(0xFFE7E7E9),
            focusedSupportingTextColor = Color.Gray,
            unfocusedSupportingTextColor = Color.Gray
        ),
    )
}

@Composable
fun OutlinedTextFieldPassword(
    value: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(label, fontFamily = RadioCanadaRegular) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(
                        id = if (passwordVisible) R.drawable.openeyes else R.drawable.closeeyes
                    ),
                    contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль",
                    modifier = Modifier.padding(4.dp)
                )
            }
        },
        shape = RoundedCornerShape(17.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedLabelColor = Color.Black,
            focusedContainerColor = Color(0xFFE7E7E9),
            unfocusedContainerColor = Color(0xFFE7E7E9),
            focusedSupportingTextColor = Color.Gray,
            unfocusedSupportingTextColor = Color.Gray,
            errorContainerColor = Color(0xFFE7E7E9),
            errorTextColor = Color.Red,
            errorLabelColor = Color.Red,
            errorBorderColor = Color.Red
        )
    )
}

@Composable
fun CustomDropDownField(
    value: String,
    placeholder: String,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFE7E7E9),
                shape = RoundedCornerShape(17.dp)
            )
            .clickable { onExpandedChange(true) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (value.isNotEmpty()) value else placeholder,
                color = if (value.isNotEmpty()) Color.Black else Color.Gray,
                fontSize = 14.sp,
                fontFamily = RadioCanadaRegular,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                maxLines = Int.MAX_VALUE,
                softWrap = true,
                overflow = TextOverflow.Visible
            )

            IconButton(
                onClick = { onExpandedChange(true) },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.down),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
            }
        }
    }
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
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color(0xFFD9D9D9),
            unfocusedContainerColor = Color(0xFFD9D9D9),
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
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color(0xFFD9D9D9),
            unfocusedContainerColor = Color(0xFFD9D9D9),
            disabledBorderColor = Color.Transparent,
            disabledTextColor = Color.Black,
            disabledLabelColor = Color.Gray,
            disabledPlaceholderColor = Color.Gray,
            disabledContainerColor = Color(0xFFD9D9D9),
            disabledSupportingTextColor = Color.Gray,
            disabledLeadingIconColor = Color.Gray,
            disabledTrailingIconColor = Color.Gray

        ),
        enabled = false,
    )
}

@Composable
fun OutlinedTextFieldNormalDigital(value: Int, onvaluechange: (String) -> Unit) {

    val displayValue = remember(value) {
        if (value != 0) {
            "%,d ₽".format(value).replace(",", " ")
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
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedContainerColor = Color(0xFFE7E7E9),
            unfocusedContainerColor = Color(0xFFE7E7E9),
            focusedSupportingTextColor = Color.Gray,
            unfocusedSupportingTextColor = Color.Gray
        ),
    )
}

@Composable
fun MoneyAmountTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .height(35.dp)
            .width(85.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(start = 8.dp, end = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                val digitsOnly = newValue.filter { it.isDigit() }
                val limitedDigits = digitsOnly.take(7)
                val formatted = limitedDigits.reversed().chunked(3).joinToString(" ").reversed()
                onValueChange(formatted)
            },
            textStyle = TextStyle(
                fontFamily = RadioCanadaRegular,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxSize(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = "0",
                            fontSize = 15.sp,
                            fontFamily = RadioCanadaRegular,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun TextFieldDigital(value: String, onvaluechange: (String) -> Unit) {


    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Извлекаем только цифры из введённого текста
            val digitsOnly = newValue.filter { it.isDigit() }
            onvaluechange(digitsOnly)
        },
        placeholder = {
            Text(
                text = "1234",
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
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedContainerColor = Color(0xFFE7E7E9),
            unfocusedContainerColor = Color(0xFFE7E7E9),
            focusedSupportingTextColor = Color.Gray,
            unfocusedSupportingTextColor = Color.Gray
        ),
    )
}