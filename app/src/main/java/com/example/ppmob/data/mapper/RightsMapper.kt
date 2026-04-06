package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.RightsDto
import com.example.ppmob.domain.model.Rights

object RightsMapper {
    // Преобразование одного RightsDto в доменную модель Rights
    fun toDomain(rightsDto: RightsDto): Rights {
        return Rights(
            id=rightsDto.id,
            name = rightsDto.name
        )
    }

    // Преобразование списка DTO в список доменных моделей Rights
    fun toDomainList(rightsDtos: List<RightsDto>): List<Rights> {
        return rightsDtos.map { item ->
            toDomain(
                item
            )
        }
    }
}