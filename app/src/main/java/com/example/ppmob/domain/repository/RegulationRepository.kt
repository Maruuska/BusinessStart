package com.example.ppmob.domain.repository

import com.example.ppmob.domain.model.Duty
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Rights

interface RegulationRepository {

    // Приостанавливающая функция для получения списка всех прав
    suspend fun getRights(): Rezult<List<Rights>>

    // Приостанавливающая функция для создания обязанности
    suspend fun createRights(
        name: String
    ): Rezult<Boolean>

    suspend fun getDuties(): Rezult<List<Duty>>

    // Приостанавливающая функция для создания обязанности
    suspend fun createDuty(
        name: String
    ): Rezult<Boolean>
}