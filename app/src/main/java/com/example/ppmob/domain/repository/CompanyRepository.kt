package com.example.ppmob.domain.repository

import com.example.ppmob.domain.model.Activity
import com.example.ppmob.domain.model.Address
import com.example.ppmob.domain.model.Company
import com.example.ppmob.domain.model.Rezult
import java.util.UUID

interface CompanyRepository {
    // Приостанавливающая функция для создания компании
    suspend fun createCompany(
        name: String,
        shortName: String,
        addressId: Int,
        activityId: Int,
        oneFounder: Boolean,
        userId: UUID
    ): Rezult<Company>

    // Приостанавливающая функция для получения списка всех адресов
    suspend fun getAddresses(): Rezult<List<Address>>

    // Приостанавливающая функция для получения списка всех видов деятельности
    suspend fun getActivitys(): Rezult<List<Activity>>

    // Приостанавливающая функция для получения последней компании пользователя
    suspend fun getLastCompany(userId: String): Rezult<Company>
}