package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Bank
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.BankRepository
import javax.inject.Inject

class GetBanksUseCase @Inject constructor(
    private val bankRepository: BankRepository
){
    suspend operator fun invoke(): Rezult<List<Bank>> {
        return bankRepository.getBanks()
    }
}