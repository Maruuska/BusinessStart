package com.example.ppmob.presentation.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.ui.theme.ActiveGreen
import com.example.ppmob.ui.theme.NoActiveGreen
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold
import kotlinx.coroutines.delay

@Composable
fun MeetingScreen(navController: NavHostController) {
    // Состояния для прогресс-бара и голосования
    var progress by remember { mutableStateOf(0f) } // 0f, 0.33f, 0.66f, 1f
    var isVoted by remember { mutableStateOf(false) }
    var voteResult by remember { mutableStateOf<String?>(null) } // "FOR"
    var currentImage by remember { mutableStateOf(R.drawable.tablevoting) }

    var selectedLicvidator by remember { mutableStateOf(0) }

    // Эффект для обработки голосования и анимации
    LaunchedEffect(voteResult) {
        if (voteResult != null) {

            when (voteResult) {
                "FOR" -> {
                    progress = 0.33f
                    delay(1000)

                    currentImage = R.drawable.one
                    progress = 0.66f
                    delay(1000)

                    currentImage = R.drawable.two
                    progress = 1f
                    delay(1000)

                    navController.navigate(NavRoutes.notify)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Собрание участников ООО",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 18.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(90.dp))

        // прогресс бар
        ProgressBar(progress = progress)

        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = currentImage),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp, top = 145.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFEEE9DD), shape = RoundedCornerShape(22.dp))
                    .padding(20.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Кто будет назначен ликвидатором?",
                    fontSize = 17.sp,
                    fontFamily = RadioCanadaMedium,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = selectedLicvidator == 0,
                        onClick = {
                            selectedLicvidator = 0
                        },
                        modifier = Modifier.size(12.dp),
                        colors = androidx.compose.material3.RadioButtonDefaults.colors(
                            selectedColor = Color.Black,
                            unselectedColor = Color.Black
                        )
                    )

                    Text(
                        text = "Иностранный учредитель ООО",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = selectedLicvidator == 1,
                        onClick = {
                            selectedLicvidator = 1
                        },
                        modifier = Modifier.size(12.dp),
                        colors = androidx.compose.material3.RadioButtonDefaults.colors(
                            selectedColor = Color.Black,
                            unselectedColor = Color.Black
                        )
                    )
                    Text(
                        text = "Директор",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = selectedLicvidator == 2,
                        onClick = {
                            selectedLicvidator = 2
                        },
                        modifier = Modifier.size(12.dp),
                        colors = androidx.compose.material3.RadioButtonDefaults.colors(
                            selectedColor = Color.Black,
                            unselectedColor = Color.Black
                        )
                    )
                    Text(
                        text = "Бывший сотрудник компании",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = selectedLicvidator == 3,
                        onClick = {
                            selectedLicvidator = 3
                        },
                        modifier = Modifier.size(12.dp),
                        colors = androidx.compose.material3.RadioButtonDefaults.colors(
                            selectedColor = Color.Black,
                            unselectedColor = Color.Black
                        )
                    )
                    Text(
                        text = "Иное лицо",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))


                ButtonCustom(
                    "ГОЛОСОВАТЬ",
                    !isVoted,
                    ActiveGreen,
                    NoActiveGreen,
                    16.sp,
                    14.dp,
                    120.dp,
                    45.dp
                ) {
                    if (!isVoted) {
                        isVoted = true
                        voteResult = "FOR"
                    }
                }
            }
        }
    }
}