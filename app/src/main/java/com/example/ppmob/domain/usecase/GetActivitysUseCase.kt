package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Activity
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.CompanyRepository
import javax.inject.Inject

class GetActivitysUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
){
    suspend operator fun invoke(): Rezult<List<Activity>> {
        return companyRepository.getActivitys()
    }
}