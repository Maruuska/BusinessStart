package com.example.ppmob.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.R
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaMedium
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun StaffScreen(navController: NavHostController) {

    var messageText by remember { mutableStateOf("") }
    var showNotification by remember { mutableStateOf(false) }
    var isSending by remember { mutableStateOf(false) }

    fun sendMessage() {
        if (messageText.isNotBlank() && !isSending) {
            isSending = true
            showNotification = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 70.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Уведомление сотрудников",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(35.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Владелец компании обязан уведомить сотрудников о предстоящем увольнении в связи с ликвидацией не менее чем за два месяца",
                    fontFamily = RadioCanadaMedium,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(start = 12.dp),
                    lineHeight = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Нажмите на поле ввода и напишите сообщение",
                fontFamily = RadioCanadaRegular,
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = TextAlign.Left,
                lineHeight = 15.sp
            )
            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 15.dp, start = 15.dp, end = 15.dp)
                        .height(130.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BasicTextField(
                            value = messageText,
                            onValueChange = { messageText = it },
                            textStyle = TextStyle(
                                fontFamily = RadioCanadaRegular,
                                fontSize = 14.sp,
                                color = Color.Black
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    if (messageText.isEmpty()) {
                                        Text(
                                            text = "Введите текст сообщения...",
                                            fontSize = 14.sp,
                                            fontFamily = RadioCanadaRegular,
                                            color = Color.Gray
                                        )
                                    } else {
                                        Column {
                                            Text(
                                                text = "Уважаемые коллеги!",
                                                fontSize = 14.sp,
                                                fontFamily = RadioCanadaRegular,
                                                color = Color.Black
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = messageText,
                                                fontSize = 14.sp,
                                                fontFamily = RadioCanadaRegular,
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Image(
                            painter = painterResource(id = R.drawable.send),
                            contentDescription = "Отправить",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .clickable(
                                    enabled = messageText.isNotBlank() && !isSending
                                ) {
                                    sendMessage()
                                }
                                .padding(8.dp),
                        )
                    }
                }
            }

            if (showNotification) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "✓ Сообщение отправлено",
                        fontFamily = RadioCanadaRegular,
                        fontSize = 13.sp,
                        color = Color(0xFF2E7D32),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            ButtonCustom(
                label = "Далее",
                enbl = isSending,
                activeColor = ActiveBlue,
                noActiveColor = NoActiveBlue,
                fontSize = 16.sp,
                rounded = 14.dp,
                buttonWidth = 200.dp,
                buttonHeight = 45.dp
            ) {
                navController.navigate(NavRoutes.creditors)
            }
        }
    }
}