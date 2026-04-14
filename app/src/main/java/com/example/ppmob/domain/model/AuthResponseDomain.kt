package com.example.ppmob.domain.model

// структура для API овета
data class AuthResponseDomain(
    val accessToken: String,
    val user: User
)