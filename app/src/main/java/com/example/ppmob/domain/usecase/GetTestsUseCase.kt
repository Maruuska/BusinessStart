package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Test
import com.example.ppmob.domain.repository.TestRepository
import javax.inject.Inject

class GetTestsUseCase @Inject constructor(
    private val testRepository: TestRepository
){
    suspend operator fun invoke(): Rezult<List<Test>> {
        return testRepository.getTests()
    }
}