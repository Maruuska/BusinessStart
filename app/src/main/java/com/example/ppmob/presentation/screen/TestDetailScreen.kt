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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.domain.model.Answer
import com.example.ppmob.domain.state.TestDetailUiState
import com.example.ppmob.presentation.viewmodel.TestDetailViewModel
import com.example.ppmob.ui.theme.ActiveBlue
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
                navController.navigate("passed_screen/${uiState.testName}/$countBalls/$total") {
                    popUpTo(NavRoutes.tests) { inclusive = false }
                }
            } else {
                navController.navigate("not_passed_screen/${uiState.testName}/$countBalls/$total") {
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
    onNextQuestion: () -> Unit
) {
    val currentQuestion = uiState.currentQuestion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Название теста
        Text(
            text = uiState.testName,
            fontFamily = RadioCanadaSemiBold,
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Блок с вопросом (синий фон)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(ActiveBlue)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentQuestion?.question?.text ?: "",
                fontFamily = RadioCanadaSemiBold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Варианты ответов
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

        // Сообщение о результате
        if (uiState.showResultMessage && !uiState.isAnswerChecked) {
            Text(
                text = if (uiState.isAnswerCorrect) "✓ Верно!" else "✗ Неверно",
                color = if (uiState.isAnswerCorrect) Color.Green else Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
        }

        // Кнопка "Ответить"
        Button(
            onClick = onCheckAnswer,
            enabled = uiState.selectedAnswer != null && !uiState.isAnswerChecked,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                disabledContainerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Ответить",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Кнопка "Далее"
        Button(
            onClick = onNextQuestion,
            enabled = uiState.isAnswerChecked,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = ActiveBlue,
                disabledContainerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (uiState.currentQuestionIndex + 1 < uiState.questions.size) "Далее" else "Завершить",
                color = Color.White,
                fontSize = 16.sp
            )
        }


    }
}

@Composable
private fun AnswerItem(
    answer: Answer,
    isSelected: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                when {
                    isSelected && !isDisabled -> ActiveBlue
                    else -> Color(0xFFE0E0E0)
                }
            )
            .clickable(enabled = !isDisabled) { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = answer.text,
            color = if (isSelected && !isDisabled) Color.White else Color.Black,
            fontSize = 16.sp,
            fontFamily = RadioCanadaRegular
        )
    }
}