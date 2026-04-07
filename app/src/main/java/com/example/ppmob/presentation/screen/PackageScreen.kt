package com.example.ppmob.presentation.screen

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.Coil.imageLoader
import coil.ImageLoader
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.OutlinedTextFieldNormal
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold
import kotlinx.coroutines.delay

@Composable
fun PackageScreen(
    navController: NavHostController,
) {
    // Состояние для диалога
    var showDialog by remember { mutableStateOf(false) }
    // Состояние для обратного отсчета
    var countdown by remember { mutableStateOf(3) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 100.dp, start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Итоговый пакет документов",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 19.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(100.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.p11001),
                    contentDescription = "",
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Заявление по форме Р11001",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.reshenie),
                    contentDescription = "",
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Решение о создании",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "(1 учредитель)",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dogovor),
                    contentDescription = "",
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Договор об учреждении (более 1 учредителя)",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ystav),
                    contentDescription = "",
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Устав",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.kvitancia),
                    contentDescription = "",
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Квитанция об оплате госпошлины",
                    fontFamily = RadioCanadaRegular,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
        ButtonCustom(
            "Зарегистрировать ООО",
            true,
            ActiveBlue,
            NoActiveBlue,
            16.sp,
            14.dp,
            230.dp,
            45.dp
        ) {
            showDialog = true
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    GifImage()
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = when (countdown) {
                            3 -> "3 дня"
                            2 -> "2 дня"
                            1 -> "1 день"
                            else -> ""
                        },
                        fontFamily = RadioCanadaSemiBold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    LaunchedEffect(showDialog) {
        // отсчет
        for (i in 3 downTo 1) {
            countdown = i
            delay(1000L)
        }
        if (showDialog) {
            showDialog = false
            navController.navigate(NavRoutes.endCompany)
        }
    }
}

@Composable
fun GifImage() {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = R.drawable.timer).apply(block = {
                size(200)
            }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = Modifier.size(150.dp)
    )
}