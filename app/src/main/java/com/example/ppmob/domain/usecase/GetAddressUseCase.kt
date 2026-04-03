package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Address
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.CompanyRepository
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
){
    suspend operator fun invoke(): Rezult<List<Address>> {
        return companyRepository.getAddresses()
    }
}