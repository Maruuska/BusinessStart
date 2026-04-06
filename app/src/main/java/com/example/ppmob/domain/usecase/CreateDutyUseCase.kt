package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.RegulationRepository
import javax.inject.Inject

class CreateDutyUseCase @Inject constructor(
    private val regulationRepository: RegulationRepository
) {
    suspend operator fun invoke(
        name: String
    ): Rezult<Boolean> {
        return regulationRepository.createDuty(name)
    }
}