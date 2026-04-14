package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Company
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.CompanyRepository
import javax.inject.Inject

class GetLastCompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(
        userId: String
    ): Rezult<Company> {
        return companyRepository.getLastCompany(userId)
    }
}