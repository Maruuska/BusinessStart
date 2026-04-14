package com.example.ppmob.data.dto

import java.util.UUID

// созданный пользователь
data class AuthUserDto(
    val id: UUID,
    val email: String,
)