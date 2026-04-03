package com.example.ppmob.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppmob.ui.theme.RadioCanadaRegular


@Composable
fun ButtonCustom(
    label: String,
    enbl: Boolean,
    activeColor: Color,
    noActiveColor: Color,
    fontSize: TextUnit,
    rounded:Dp,
    buttonWidth: Dp = 150.dp,
    buttonHeight: Dp = 53.dp,
    onClick: () -> Unit
) {

    val colorCont: Color = if (enbl) {
        activeColor
    } else {
        noActiveColor
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorCont,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(rounded),
        enabled = enbl,
        modifier = Modifier
            .width(buttonWidth)
            .height(buttonHeight)
    ) {
        Text(text = label, color = Color.White, fontFamily = RadioCanadaRegular, fontSize = fontSize)
    }
}