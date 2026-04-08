package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.CodeCountry
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.StatementRepository
import javax.inject.Inject

class GetCodeCountryUseCase @Inject constructor(
    private val statementRepository: StatementRepository
){
    suspend operator fun invoke(): Rezult<List<CodeCountry>> {
        return statementRepository.getCodeCountries()
    }
}