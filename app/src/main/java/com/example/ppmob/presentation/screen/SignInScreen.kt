package com.example.ppmob.presentation.screen

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.components.OutlinedTextFieldEmail
import com.example.ppmob.presentation.components.OutlinedTextFieldNormal
import com.example.ppmob.presentation.components.OutlinedTextFieldPassword
import com.example.ppmob.presentation.viewmodel.SignInViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun SignInScreen(
    navController: NavHostController,
    signInViewModel: SignInViewModel = hiltViewModel(),
) {
    val fieldsSignIn by signInViewModel.fieldsSignIn.collectAsState()
    val appState by signInViewModel.appState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Авторизация",
            fontFamily = RadioCanadaSemiBold,
            fontSize = 19.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 140.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Поле ввода Email
            Text(
                text = "Email",
                fontFamily = RadioCanadaRegular,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextFieldEmail(fieldsSignIn.email){
                signInViewModel.updateState(
                    fieldsSignIn.copy(email = it)
                )
            }
            // Вывод о неправильном формате email
            if (fieldsSignIn.errorEmail) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "некорректный формат email",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = RadioCanadaRegular,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Поле ввода Пароля
            Text(
                text = "Пароль",
                fontFamily = RadioCanadaRegular,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextFieldPassword(fieldsSignIn.password,""){
                signInViewModel.updateState(
                    fieldsSignIn.copy(password = it)
                )
            }
            // Вывод о неправильном формате пароля
            if (fieldsSignIn.errorPassword) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "пароль меньше 6 символов",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = RadioCanadaRegular,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            when (appState) {
                is AppState.Loading -> {
                    ButtonCustom(
                        "Войти",
                        false,
                        ActiveBlue,
                        NoActiveBlue,
                        16.sp,
                        14.dp,
                        130.dp,
                        45.dp
                    ) {
                        signInViewModel.signIn()
                    }
                }

                is AppState.Initializing -> {
                    ButtonCustom(
                        "Войти",
                        true,
                        ActiveBlue,
                        NoActiveBlue,
                        16.sp,
                        14.dp,
                        130.dp,
                        45.dp
                    ) {
                        signInViewModel.signIn()
                    }
                }

                is AppState.Success -> {
                    navController.navigate(NavRoutes.menu) {
                        popUpTo(NavRoutes.signin) { inclusive = true }
                    }
                }

                is AppState.Error -> {
                    ButtonCustom(
                        "Войти",
                        true,
                        ActiveBlue,
                        NoActiveBlue,
                        16.sp,
                        14.dp,
                        130.dp,
                        45.dp
                    ) {
                        signInViewModel.signIn()
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = (appState as AppState.Error).message,
                        fontFamily = RadioCanadaRegular,
                        fontSize = 12.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Нет аккаунта? ",
                fontFamily = RadioCanadaRegular,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Зарегистрироваться",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 14.sp,
                color = ActiveBlue,
                modifier = Modifier.clickable {
                    navController.navigate(NavRoutes.signup)
                }
            )
        }
    }
}