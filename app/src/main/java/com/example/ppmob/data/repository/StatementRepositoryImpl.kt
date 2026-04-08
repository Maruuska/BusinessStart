package com.example.ppmob.data.repository

import com.example.ppmob.data.mapper.CodeMapper
import com.example.ppmob.data.mapper.FormMapper
import com.example.ppmob.data.remote.ApiInterface
import com.example.ppmob.domain.model.CodeCountry
import com.example.ppmob.domain.model.Form
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.StatementRepository
import javax.inject.Inject

class StatementRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
): StatementRepository {
    override suspend fun getCodeCountries(): Rezult<List<CodeCountry>> {
        return try {
            val codeDtos = apiInterface.getCodeCountries()
            val codes = CodeMapper.toDomainList(codeDtos)
            Rezult.Success(codes)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }

    override suspend fun getForms(): Rezult<List<Form>> {
        return try {
            val formDtos = apiInterface.getForms()
            val forms = FormMapper.toDomainList(formDtos)
            Rezult.Success(forms)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }
}