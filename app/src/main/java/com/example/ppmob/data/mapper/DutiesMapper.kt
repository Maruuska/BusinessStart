package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.DutyDto
import com.example.ppmob.domain.model.Duty

object DutiesMapper {
    // Преобразование одного DutyDto в доменную модель Duty
    fun toDomain(dutyDto: DutyDto): Duty {
        return Duty(
            id=dutyDto.id,
            name = dutyDto.name
        )
    }

    // Преобразование списка DTO в список доменных моделей Duty
    fun toDomainList(dutiesDtos: List<DutyDto>): List<Duty> {
        return dutiesDtos.map { item ->
            toDomain(
                item
            )
        }
    }
}