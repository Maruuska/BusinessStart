package com.example.ppmob.data.repository

import com.example.ppmob.data.mapper.BankMapper
import com.example.ppmob.data.mapper.CountryMapper
import com.example.ppmob.data.remote.ApiInterface
import com.example.ppmob.domain.model.Bank
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.BankRepository
import javax.inject.Inject

class BankRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
): BankRepository {
    override suspend fun getBanks(): Rezult<List<Bank>> {
        return try {
            val bankDtos = apiInterface.getBanks()
            val banks = BankMapper.toDomainList(bankDtos)
            Rezult.Success(banks)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }
}