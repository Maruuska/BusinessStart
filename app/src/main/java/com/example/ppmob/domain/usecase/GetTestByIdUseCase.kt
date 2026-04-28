package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Test
import com.example.ppmob.domain.repository.TestRepository
import javax.inject.Inject

class GetTestByIdUseCase @Inject constructor(
    private val repository: TestRepository
) {
    suspend operator fun invoke(testId: Int): Rezult<Test> {
        return when (val result = repository.getTests()) {
            is Rezult.Success -> {
                val test = result.data.find { it.id == testId }
                if (test != null) {
                    Rezult.Success(test)
                } else {
                    Rezult.Failure(Exception("Test not found"))
                }
            }
            is Rezult.Failure -> Rezult.Failure(result.exception)
        }
    }
}