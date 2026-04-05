package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.CountyDto
import com.example.ppmob.domain.model.Country

object CountryMapper {

    // Преобразование одного CountyDto в доменную модель County
    fun toDomain(countyDto: CountyDto): Country {
        return Country(
            id = countyDto.id,
            name = countyDto.name,
            legal = countyDto.legal,
            taxCode = countyDto.taxCode
        )
    }

    // Преобразование списка DTO в список доменных моделей County
    fun toDomainList(cardCountries: List<CountyDto>): List<Country> {
        return cardCountries.map { item ->
            toDomain(
                item
            )
        }
    }
}