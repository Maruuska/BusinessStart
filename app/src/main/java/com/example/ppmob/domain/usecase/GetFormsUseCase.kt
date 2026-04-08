package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Form
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.StatementRepository
import javax.inject.Inject

class GetFormsUseCase @Inject constructor(
    private val statementRepository: StatementRepository
){
    suspend operator fun invoke(): Rezult<List<Form>> {
        return statementRepository.getForms()
    }
}