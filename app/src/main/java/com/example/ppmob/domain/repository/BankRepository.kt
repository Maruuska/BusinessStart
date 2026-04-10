package com.example.ppmob.domain.repository

import com.example.ppmob.domain.model.Bank
import com.example.ppmob.domain.model.Rezult

interface BankRepository {
    // Приостанавливающая функция для получения списка всех банков
    suspend fun getBanks(): Rezult<List<Bank>>
}