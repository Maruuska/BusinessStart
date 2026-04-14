package com.example.ppmob.domain.repository

import com.example.ppmob.domain.model.AuthResponseDomain
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.User

interface UserRepository {
    // Приостанавливающая функция для регистрации пользователя
    suspend fun signUp(email: String, password: String): Rezult<AuthResponseDomain>

    // Приостанавливающая функция для авторизации пользователя
    suspend fun signIn(email: String, password: String): Rezult<AuthResponseDomain>

    // Приостанавливающая функция для получения текущего пользователя
    suspend fun getCurrentUser():  Rezult<User>
}