package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Company
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.CompanyRepository
import javax.inject.Inject

class CreateCompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(company: Company): Rezult<Company> {
        return companyRepository.createCompany(company)
    }
}