package com.example.ppmob.data.repository

import com.example.ppmob.data.dto.CompanyDto
import com.example.ppmob.data.dto.CreateDutyDto
import com.example.ppmob.data.dto.CreateRightDto
import com.example.ppmob.data.mapper.DutiesMapper
import com.example.ppmob.data.mapper.RightsMapper
import com.example.ppmob.data.remote.ApiInterface
import com.example.ppmob.domain.model.Duty
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Rights
import com.example.ppmob.domain.repository.RegulationRepository
import javax.inject.Inject

class RegulationRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : RegulationRepository {
    override suspend fun getRights(): Rezult<List<Rights>> {
        return try {
            val rightsDtos = apiInterface.getRights()
            val rights = RightsMapper.toDomainList(rightsDtos)
            Rezult.Success(rights)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }

    override suspend fun createRights(name: String): Rezult<Boolean> {
        return try {
            val rightDto = CreateRightDto(
                name = name,
            )
            apiInterface.createRights(rightDto)
            Rezult.Success(true)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }

    override suspend fun getDuties(): Rezult<List<Duty>> {
        return try {
            val dutiesDtos = apiInterface.getDuties()
            val duties = DutiesMapper.toDomainList(dutiesDtos)
            Rezult.Success(duties)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }

    override suspend fun createDuty(name: String): Rezult<Boolean> {
        return try {
            val dutyDto = CreateDutyDto(
                name = name
            )
            apiInterface.createDuties(dutyDto)
            Rezult.Success(true)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }
}