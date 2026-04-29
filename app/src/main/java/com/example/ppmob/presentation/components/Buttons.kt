package com.example.ppmob.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppmob.R
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular


@Composable
fun ButtonCustom(
    label: String,
    enbl: Boolean,
    activeColor: Color,
    noActiveColor: Color,
    fontSize: TextUnit,
    rounded: Dp,
    buttonWidth: Dp = 150.dp,
    buttonHeight: Dp = 53.dp,
    isActive: Boolean = true,
    onClick: () -> Unit,
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

@Composable
fun ButtonCustom2(
    label: String,
    enbl: Boolean,
    activeColor: Color,
    noActiveColor: Color,
    fontSize: TextUnit,
    rounded: Dp,
    buttonWidth: Dp = 150.dp,
    buttonHeight: Dp = 53.dp,
    isActive: Boolean = true,
    onClick: () -> Unit,
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
            color = Color.White,
            lineHeight = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun ButtonCustomOutline(
    label: String,
    enbl: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonHeight: Dp = 53.dp,
    fontSize: TextUnit = 16.sp,
    rounded: Dp = 14.dp,
    iconRes: Int = R.drawable.skrepka, // иконка скрепки
) {
    Box(
        modifier = modifier
            .height(buttonHeight)
            .clip(RoundedCornerShape(rounded))
            .border(
                width = 1.dp,
                color = ActiveBlue,
                shape = RoundedCornerShape(rounded)
            )
            .background(Color.White)
            .clickable(enabled = enbl) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(modifier= Modifier.padding(start = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "",
                modifier = Modifier.size(20.dp),
                tint = Color.Black
            )

            Text(
                text = label,
                fontSize = fontSize,
                fontFamily = RadioCanadaRegular,
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp),
                lineHeight = 13.sp
            )
        }
    }
}

@Composable
fun ButtonCustomOutline2(
    label: String,
    enbl: Boolean = true,
    onClick: () -> Unit,
    buttonWidth: Dp = 150.dp,
    buttonHeight: Dp = 53.dp,
    fontSize: TextUnit = 16.sp,
    rounded: Dp = 10.dp,
    iconRes: Int = R.drawable.skrepka,
) {
    Box(
        modifier = Modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .clip(RoundedCornerShape(rounded))
            .border(
                width = 1.dp,
                color = ActiveBlue,
                shape = RoundedCornerShape(rounded)
            )
            .background(Color.White)
            .clickable(enabled = enbl) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "",
                modifier = Modifier.size(20.dp),
                tint = Color.Black
            )

            Text(
                text = label,
                fontSize = fontSize,
                fontFamily = RadioCanadaRegular,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )

        }
    }
}