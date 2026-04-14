package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Company
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.CompanyRepository
import java.util.UUID
import javax.inject.Inject

class CreateCompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(
        name: String,
        shortName: String,
        addressId: Int,
        activityId: Int,
        oneFounder: Boolean,
        userId: UUID
    ): Rezult<Company> {
        return companyRepository.createCompany(name,shortName,addressId,activityId,oneFounder,userId)
    }
}