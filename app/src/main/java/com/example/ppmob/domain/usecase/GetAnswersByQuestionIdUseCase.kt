package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Answer
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.TestRepository
import javax.inject.Inject

class GetAnswersByQuestionIdUseCase @Inject constructor(
    private val repository: TestRepository
) {
    suspend operator fun invoke(questionId: Int): Rezult<List<Answer>> {
        return when (val result = repository.getAnswers()) {
            is Rezult.Success -> {
                val answers = result.data.filter { it.questionId == questionId }
                Rezult.Success(answers)
            }
            is Rezult.Failure -> Rezult.Failure(result.exception)
        }
    }
}