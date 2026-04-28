package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Question
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.TestRepository
import javax.inject.Inject

class GetQuestionsByTestIdUseCase @Inject constructor(
    private val repository: TestRepository
) {
    suspend operator fun invoke(testId: Int): Rezult<List<Question>> {
        return when (val result = repository.getQuestions()) {
            is Rezult.Success -> {
                val questions = result.data.filter { it.testId == testId }
                Rezult.Success(questions)
            }
            is Rezult.Failure -> Rezult.Failure(result.exception)
        }
    }
}