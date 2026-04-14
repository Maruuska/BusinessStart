package com.example.ppmob.data.dto

import com.google.gson.annotations.SerializedName

// ответ от сервера с созданным пользователем
data class AuthResponse(
    @SerializedName("access_token")
    val accessToken: String,
    val user: AuthUserDto
)