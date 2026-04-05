package com.example.ppmob.domain.repository

import com.example.ppmob.domain.model.Country
import com.example.ppmob.domain.model.Rezult

interface CountryRepository {

    // Приостанавливающая функция для получения списка всех стран
    suspend fun getCountries(): Rezult<List<Country>>
}