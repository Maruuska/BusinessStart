package com.example.ppmob.domain.repository

import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Rights

interface RegulationRepository {

    // Приостанавливающая функция для получения списка всех прав
    suspend fun getRights(): Rezult<List<Rights>>

    // Приостанавливающая функция для создания права
    suspend fun createRights(
        name: String
    ): Rezult<Boolean>
}