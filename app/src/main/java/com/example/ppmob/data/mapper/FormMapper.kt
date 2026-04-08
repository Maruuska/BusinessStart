package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.FormDto
import com.example.ppmob.domain.model.Form

object FormMapper {

    // Преобразование одного FormDto в доменную модель Form
    fun toDomain(formDto: FormDto): Form {
        return Form(
            id = formDto.id,
            name = formDto.name
        )
    }

    // Преобразование списка DTO в список доменных моделей Form
    fun toDomainList(forms: List<FormDto>): List<Form> {
        return forms.map { item ->
            toDomain(
                item
            )
        }
    }
}