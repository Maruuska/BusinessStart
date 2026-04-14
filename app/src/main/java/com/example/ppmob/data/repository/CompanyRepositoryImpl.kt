package com.example.ppmob.data.repository

import android.util.Log
import com.example.ppmob.data.dto.CompanyDto
import com.example.ppmob.data.mapper.ActivityMapper
import com.example.ppmob.data.mapper.AddressMapper
import com.example.ppmob.data.mapper.CompanyMapper
import com.example.ppmob.data.remote.ApiInterface
import com.example.ppmob.domain.model.Activity
import com.example.ppmob.domain.model.Company
import com.example.ppmob.domain.model.Address
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.CompanyRepository
import java.util.UUID
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
): CompanyRepository {

    // переопределение абстрактной функции для создания компании
    override suspend fun createCompany(
        name: String,
        shortName: String,
        addressId: Int,
        activityId: Int,
        oneFounder: Boolean,
        userId: UUID
    ): Rezult<Company> {
        return try{
            val companyDto = CompanyDto(
                name = name,
                shortName=shortName,
                address=addressId,
                typeActivity = activityId,
                oneFounder=oneFounder,
                userId =userId
            )
            apiInterface.createCompany(companyDto)
            Rezult.Success(CompanyMapper.toDomain(companyDto))
        }
        catch (e: Exception) {
            Rezult.Failure(e)
        }
    }

    // переопределение абстрактной функции для получения списка адресов
    override suspend fun getAddresses(): Rezult<List<Address>> {
        return try {
            val adressDto = apiInterface.getAddresses()
            val addresses = AddressMapper.toDomainList(adressDto)
            Rezult.Success(addresses)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }

    // переопределение абстрактной функции для получения списка видов деятельности
    override suspend fun getActivitys(): Rezult<List<Activity>> {
        return try {
            val activitysDto = apiInterface.getActivitys()
            val activitys = ActivityMapper.toDomainList(activitysDto)
            Rezult.Success(activitys)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }
}