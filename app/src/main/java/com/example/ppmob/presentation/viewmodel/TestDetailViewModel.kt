package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Answer
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.TestDetailUiState
import com.example.ppmob.domain.usecase.GetFullTestDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestDetailViewModel @Inject constructor(
    private val getFullTestDataUseCase: GetFullTestDataUseCase,  // получение теста со всеми вопросами и ответами к ним
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val testId: String = checkNotNull(savedStateHandle["id"])  // id теста

    private val _uiState = MutableStateFlow(TestDetailUiState())
    val uiState: StateFlow<TestDetailUiState> = _uiState.asStateFlow()

    init {
        loadTestData()
    }

    private fun loadTestData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            when (val result = getFullTestDataUseCase(testId.toInt())) {
                is Rezult.Success -> {

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        testName = result.data.test.name,
                        questions = result.data.questions,
                        currentQuestionIndex = 0,
                        isFinished = result.data.questions.isEmpty(),
                        correctAnswersCount = 0
                    )
                }
                is Rezult.Failure -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.exception.message
                    )
                }
            }
        }
    }

    fun selectAnswer(answer: Answer) {
        if (_uiState.value.isAnswerChecked) return

        _uiState.value = _uiState.value.copy(
            selectedAnswer = answer,
            showResultMessage = false,
        )
    }

    fun checkAnswer() {
        val selectedAnswer = _uiState.value.selectedAnswer
        if (selectedAnswer == null) return

        // поиск правильного ответа для текущего вопроса
        val currentQuestion = _uiState.value.currentQuestion
        val correctAnswer = currentQuestion?.answers?.find { it.isCorrect }
        val correctAnswerText = correctAnswer?.text ?: ""
        val isCorrect = selectedAnswer.isCorrect
        val currentCount = _uiState.value.correctAnswersCount

        _uiState.value = _uiState.value.copy(
            isAnswerChecked = true,
            showResultMessage = true,
            isAnswerCorrect = isCorrect,
            correctAnswersCount = if (isCorrect) currentCount + 1 else currentCount,
            correctAnswerText = if (!isCorrect) correctAnswerText else ""
        )
    }

    fun nextQuestion() {
        val currentIndex = _uiState.value.currentQuestionIndex
        val totalQuestions = _uiState.value.questions.size

        if (currentIndex + 1 < totalQuestions) {
            // переход к следующему вопросу
            _uiState.value = _uiState.value.copy(
                currentQuestionIndex = currentIndex + 1,
                selectedAnswer = null,
                isAnswerChecked = false,
                showResultMessage = false,
                isAnswerCorrect = false,
                correctAnswerText = ""
            )
        } else {
            // тест завершен
            _uiState.value = _uiState.value.copy(
                isFinished = true
            )
        }
    }

//    fun resetForRetry() {
//        _uiState.value = _uiState.value.copy(
//            currentQuestionIndex = 0,
//            selectedAnswer = null,
//            isAnswerChecked = false,
//            showResultMessage = false,
//            isAnswerCorrect = false,
//            isFinished = false
//        )
//    }

    // получение результата теста
    fun getTestResult(): Pair<Int, Int> {
        return Pair(_uiState.value.correctAnswersCount, _uiState.value.totalQuestions)
    }
}