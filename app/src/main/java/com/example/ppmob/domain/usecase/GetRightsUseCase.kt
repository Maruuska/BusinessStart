package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Rights
import com.example.ppmob.domain.repository.RegulationRepository
import javax.inject.Inject

class GetRightsUseCase @Inject constructor(
    private val regulationRepository: RegulationRepository
){
    suspend operator fun invoke(): Rezult<List<Rights>> {
        return regulationRepository.getRights()
    }
}