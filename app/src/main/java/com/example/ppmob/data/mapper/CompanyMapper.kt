package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.CompanyDto
import com.example.ppmob.domain.model.Company

object CompanyMapper {

    // Преобразование Company в модель CompanyDto
    fun toDomain(company: CompanyDto ):  Company{
        return Company(
            name = company.name,
            shortName = company.shortName,
            address= company.address,
            typeActivity = company.typeActivity,
            oneFounder=company.oneFounder,
            userId = company.userId
        )
    }
}