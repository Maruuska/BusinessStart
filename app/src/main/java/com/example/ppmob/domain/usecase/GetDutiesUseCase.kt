package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Duty
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.RegulationRepository
import javax.inject.Inject

class GetDutiesUseCase @Inject constructor(
    private val regulationRepository: RegulationRepository
){
    suspend operator fun invoke(): Rezult<List<Duty>> {
        return regulationRepository.getDuties()
    }
}