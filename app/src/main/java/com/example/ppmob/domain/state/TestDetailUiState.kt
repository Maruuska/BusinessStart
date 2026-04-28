package com.example.ppmob.domain.state

import com.example.ppmob.domain.model.Answer
import com.example.ppmob.domain.usecase.QuestionWithAnswers

data class TestDetailUiState(
    val isLoading: Boolean = false,
    val testName: String = "",
    val questions: List<QuestionWithAnswers> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswer: Answer? = null,
    val isAnswerChecked: Boolean = false,
    val showResultMessage: Boolean = false,
    val isAnswerCorrect: Boolean = false,
    val isFinished: Boolean = false,
    val error: String? = null,
    val correctAnswersCount: Int = 0,  // счетчик правильных ответов
    val correctAnswerText: String = ""  // поле для правильного ответа
) {
    val currentQuestion: QuestionWithAnswers?
        get() = questions.getOrNull(currentQuestionIndex)

    val totalQuestions: Int
        get() = questions.size
}