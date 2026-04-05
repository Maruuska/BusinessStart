package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Country
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.CountryRepository
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val countryRepository: CountryRepository
){
    suspend operator fun invoke(): Rezult<List<Country>> {
        return countryRepository.getCountries()
    }
}