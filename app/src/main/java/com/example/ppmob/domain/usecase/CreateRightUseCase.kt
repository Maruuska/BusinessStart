package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.RegulationRepository
import javax.inject.Inject

class CreateRightUseCase @Inject constructor(
    private val regulationRepository: RegulationRepository
) {
    suspend operator fun invoke(
        name: String
    ): Rezult<Boolean> {
        return regulationRepository.createRights(name)
    }
}