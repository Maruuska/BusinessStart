package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.AddressDto
import com.example.ppmob.domain.model.Address

object AddressMapper {

    // Преобразование одного AddressDto в доменную модель Address
    fun toDomain(addressDto: AddressDto): Address {
        return Address(
            id = addressDto.id,
            name = addressDto.name
        )
    }

    // Преобразование списка DTO в список доменных моделей Address
    fun toDomainList(cardAddress: List<AddressDto>): List<Address> {
        return cardAddress.map { item ->
            toDomain(
                item
            )
        }
    }
}