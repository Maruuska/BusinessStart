package com.example.ppmob.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    isActive: Boolean = true,
    onClick: () -> Unit
) {

    val buttonColor = when {
        !enbl -> noActiveColor
        isActive -> activeColor
        else -> noActiveColor
    }

    Box(
        modifier = Modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .clip(RoundedCornerShape(rounded))
            .background(buttonColor)
            .clickable(enabled = enbl) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = fontSize,
            fontFamily = RadioCanadaRegular,
            color = Color.White
        )
    }
}