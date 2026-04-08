package com.example.ppmob.domain.repository

import com.example.ppmob.domain.model.CodeCountry
import com.example.ppmob.domain.model.Form
import com.example.ppmob.domain.model.Rezult

interface StatementRepository {

    // Приостанавливающая функция для получения списка всех кодов стран
    suspend fun getCodeCountries(): Rezult<List<CodeCountry>>

    // Приостанавливающая функция для получения списка всех форм
    suspend fun getForms(): Rezult<List<Form>>
}