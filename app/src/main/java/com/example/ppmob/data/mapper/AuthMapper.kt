package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.AuthResponse
import com.example.ppmob.data.dto.AuthUserDto
import com.example.ppmob.domain.model.AuthResponseDomain
import com.example.ppmob.domain.model.User

// объект для конвертации данных при авторизации и регистрации
object AuthMapper {

    // преобразование dto.AuthResponse в domain.AuthResponseDomain
    fun mapToDomain(response: AuthResponse): AuthResponseDomain {
        return AuthResponseDomain(
            accessToken = response.accessToken,
            user = mapToDomain(response.user)
        )
    }

    // преобразование dto.AuthUserDto в domain.AuthUserDomain
    fun mapToDomain(dto: AuthUserDto): User {
        return User(
            id = dto.id,
            email = dto.email,
        )
    }
}