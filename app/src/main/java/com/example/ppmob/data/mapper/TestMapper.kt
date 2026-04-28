package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.TestDto
import com.example.ppmob.domain.model.Test

object TestMapper {
    // Преобразование одного TestDto в доменную модель Test
    fun toDomain(testDto: TestDto): Test {
        return Test(
            id = testDto.id,
            name = testDto.name,
        )
    }

    // Преобразование списка DTO в список доменных моделей Test
    fun toDomainList(tests: List<TestDto>): List<Test>   {
        return tests.map { item ->
            toDomain(
                item
            )
        }
    }
}