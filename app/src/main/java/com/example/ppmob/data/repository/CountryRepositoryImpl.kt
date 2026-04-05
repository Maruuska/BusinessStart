package com.example.ppmob.data.repository

import com.example.ppmob.data.mapper.AddressMapper
import com.example.ppmob.data.mapper.CountryMapper
import com.example.ppmob.data.remote.ApiInterface
import com.example.ppmob.domain.model.Country
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.CountryRepository
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
): CountryRepository {
    override suspend fun getCountries(): Rezult<List<Country>> {
        return try {
            val countryDtos = apiInterface.getCountries()
            val countries = CountryMapper.toDomainList(countryDtos)
            Rezult.Success(countries)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }
}