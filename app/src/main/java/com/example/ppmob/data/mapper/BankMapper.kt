package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.BankDto
import com.example.ppmob.domain.model.Bank

object BankMapper {

    // Преобразование одного BankDto в доменную модель Bank
    fun toDomain(bankDto: BankDto): Bank {
        return Bank(
            id=bankDto.id,
            name = bankDto.name,
            specialization = bankDto.specialization,
            time = bankDto.time,
            comment = bankDto.comment,
            image = bankDto.image
        )
    }

    // Преобразование списка DTO в список доменных моделей Bank
    fun toDomainList(banks: List<BankDto>): List<Bank> {
        return banks.map { item ->
            toDomain(
                item
            )
        }
    }
}