package com.example.ppmob.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.domain.model.Answer
import com.example.ppmob.domain.state.TestDetailUiState
import com.example.ppmob.presentation.components.ButtonCustom
import com.example.ppmob.presentation.viewmodel.TestDetailViewModel
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.ActiveGreen
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.NoActiveGreen
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun TestDetailScreen(
    navController: NavHostController, id: String,
    viewModel: TestDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isFinished) {
        if (uiState.isFinished && uiState.questions.isNotEmpty()) {
            val (countBalls, total) = viewModel.getTestResult()
            val isPassed = countBalls == total

            if (isPassed) {
                navController.navigate(NavRoutes.passed + "/${uiState.testName}/$countBalls/$total") {
                    popUpTo(NavRoutes.tests) { inclusive = false }
                }
            } else {
                navController.navigate(NavRoutes.noPassed + "/${uiState.testName}/$countBalls/$total") {
                    popUpTo(NavRoutes.tests) { inclusive = false }
                }
            }
        }
    }


    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (uiState.error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Ошибка: ${uiState.error}",
                color = Color.Red,
                fontSize = 16.sp
            )
        }
    } else if (!uiState.isFinished) {
        TestContent(
            uiState = uiState,
            onAnswerSelected = { viewModel.selectAnswer(it) },
            onCheckAnswer = { viewModel.checkAnswer() },
            onNextQuestion = { viewModel.nextQuestion() }
        )
    }
}

@Composable
private fun TestContent(
    uiState: TestDetailUiState,
    onAnswerSelected: (Answer) -> Unit,
    onCheckAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
) {
    val currentQuestion = uiState.currentQuestion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 65.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Тест. " + uiState.testName,
            fontFamily = RadioCanadaSemiBold,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))

        // блок с вопросом
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(ActiveBlue)
                .padding(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 35.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentQuestion?.question?.text ?: "",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // варианты ответов
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(currentQuestion?.answers?.size ?: 0) { index ->
                val answer = currentQuestion?.answers?.get(index)
                answer?.let {
                    AnswerItem(
                        answer = it,
                        isSelected = uiState.selectedAnswer?.id == it.id,
                        isDisabled = uiState.isAnswerChecked,
                        onClick = { onAnswerSelected(it) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.showResultMessage) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (uiState.isAnswerCorrect) "Верно!" else "Неверно",
                    color = if (uiState.isAnswerCorrect) Color(0xFF2E7D32) else Color(0xFFFF0000),
                    fontSize = 14.sp,
                    fontFamily = RadioCanadaRegular,
                    textAlign = TextAlign.Center
                )

                if (!uiState.isAnswerCorrect && uiState.correctAnswerText.isNotEmpty()) {
                    Text(
                        text = "Правильный ответ: ${uiState.correctAnswerText}",
                        color = Color.Red,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = RadioCanadaRegular
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        if (!uiState.isAnswerChecked) {
            Button(
                onClick = onCheckAnswer,
                enabled = uiState.selectedAnswer != null && !uiState.isAnswerChecked,
                modifier = Modifier
                    .padding(horizontal = 80.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ActiveGreen,
                    disabledContainerColor = NoActiveGreen
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Ответить",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.isAnswerChecked) {
            Button(
                onClick = onNextQuestion,
                enabled = uiState.isAnswerChecked,
                modifier = Modifier
                    .padding(horizontal = 80.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ActiveBlue,
                    disabledContainerColor = NoActiveBlue
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = if (uiState.currentQuestionIndex + 1 < uiState.questions.size) "Далее" else "Завершить",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun AnswerItem(
    answer: Answer,
    isSelected: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                when {
                    isSelected && !isDisabled -> Color(0xFFB4D6E8)
                    else -> Color(0xFFECECED)
                }
            )
            .clickable(enabled = !isDisabled) { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = answer.text,
            color = Color.Black,
            fontSize = 15.sp,
            fontFamily = RadioCanadaRegular,
            textAlign = TextAlign.Center
        )
    }
}