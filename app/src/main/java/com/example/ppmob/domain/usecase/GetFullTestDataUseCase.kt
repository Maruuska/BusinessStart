package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Answer
import com.example.ppmob.domain.model.Question
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Test
import javax.inject.Inject

data class FullTestData(
    val test: Test,
    val questions: List<QuestionWithAnswers>
)

data class QuestionWithAnswers(
    val question: Question,
    val answers: List<Answer>
)

class GetFullTestDataUseCase @Inject constructor(
    private val getTestByIdUseCase: GetTestByIdUseCase,
    private val getQuestionsByTestIdUseCase: GetQuestionsByTestIdUseCase,
    private val getAnswersByQuestionIdUseCase: GetAnswersByQuestionIdUseCase
) {
    suspend operator fun invoke(testId: Int): Rezult<FullTestData> {
        // получение теста
        val testResult = getTestByIdUseCase(testId)
        if (testResult is Rezult.Failure) {
            return Rezult.Failure(testResult.exception)
        }
        val test = (testResult as Rezult.Success).data

        // получение вопросов теста
        val questionsResult = getQuestionsByTestIdUseCase(testId)
        if (questionsResult is Rezult.Failure) {
            return Rezult.Failure(questionsResult.exception)
        }
        val questions = (questionsResult as Rezult.Success).data

        // Для каждого вопроса получение ответов
        val questionsWithAnswers = mutableListOf<QuestionWithAnswers>()
        for (question in questions) {
            val answersResult = getAnswersByQuestionIdUseCase(question.id)
            if (answersResult is Rezult.Failure) {
                return Rezult.Failure(answersResult.exception)
            }
            val answers = (answersResult as Rezult.Success).data
            questionsWithAnswers.add(
                QuestionWithAnswers(
                    question = question,
                    answers = answers
                )
            )
        }

        return Rezult.Success(
            FullTestData(
                test = test,
                questions = questionsWithAnswers
            )
        )
    }
}