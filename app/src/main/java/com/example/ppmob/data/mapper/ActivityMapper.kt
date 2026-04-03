package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.ActivityDto
import com.example.ppmob.data.dto.AddressDto
import com.example.ppmob.domain.model.Activity
import com.example.ppmob.domain.model.Address

object ActivityMapper {
    // Преобразование одного ActivityDto в доменную модель Activity
    fun toDomain(activityDto: ActivityDto): Activity {
        return Activity(
            id = activityDto.id,
            name = activityDto.name,
            license = activityDto.license
        )
    }

    // Преобразование списка DTO в список доменных моделей Activity
    fun toDomainList(cardActivitys: List<ActivityDto>): List<Activity>   {
        return cardActivitys.map { item ->
            toDomain(
                item
            )
        }
    }
}