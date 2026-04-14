package com.example.ppmob.data.dto

// данные пользователя для регистрации и авторизации
data class AuthRequest(
    val email: String,
    val password: String
)