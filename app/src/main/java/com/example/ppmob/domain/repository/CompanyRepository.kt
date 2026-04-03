package com.example.ppmob.domain.repository

import com.example.ppmob.domain.model.Activity
import com.example.ppmob.domain.model.Address
import com.example.ppmob.domain.model.Company
import com.example.ppmob.domain.model.Rezult

interface CompanyRepository {
    // Приостанавливающая функция для создания компании
    suspend fun createCompany(company: Company): Rezult<Company>

    // Приостанавливающая функция для получения списка всех адресов
    suspend fun getAddresses(): Rezult<List<Address>>

    // Приостанавливающая функция для получения списка всех видов деятельности
    suspend fun getActivitys(): Rezult<List<Activity>>
}