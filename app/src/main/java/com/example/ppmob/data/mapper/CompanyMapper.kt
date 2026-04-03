package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.CompanyDto
import com.example.ppmob.domain.model.Company
import com.google.gson.annotations.SerializedName

object CompanyMapper {

    // Преобразование Company в модель CompanyDto
    fun toDto(company: Company): CompanyDto {
        return CompanyDto(
            name = company.name,
            shortName = company.shortName,
            address= company.address,
            typeActivity = company.typeActivity,
            oneFounder=company.oneFounder
        )
    }
}