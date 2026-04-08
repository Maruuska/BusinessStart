package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.CodeCountryDto
import com.example.ppmob.domain.model.CodeCountry

object CodeMapper {

    // Преобразование одного CodeCountyDto в доменную модель CodeCounty
    fun toDomain(codeDto: CodeCountryDto): CodeCountry {
        return CodeCountry(
            id = codeDto.id,
            code = codeDto.code
        )
    }

    // Преобразование списка DTO в список доменных моделей CodeCounty
    fun toDomainList(codes: List<CodeCountryDto>): List<CodeCountry> {
        return codes.map { item ->
            toDomain(
                item
            )
        }
    }
}