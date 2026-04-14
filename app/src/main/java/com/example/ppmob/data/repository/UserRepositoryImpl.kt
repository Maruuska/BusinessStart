package com.example.ppmob.data.repository

import com.example.ppmob.data.dto.AuthRequest
import com.example.ppmob.data.dto.AuthUserDto
import com.example.ppmob.data.mapper.AuthMapper
import com.example.ppmob.data.remote.ApiInterface
import com.example.ppmob.domain.model.AuthResponseDomain
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.User
import com.example.ppmob.domain.repository.UserRepository
import java.util.UUID
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : UserRepository {

    private var currentUser: User? = null

    // метод регистрации пользователей
    override suspend fun signUp(
        email: String,
        password: String
    ): Rezult<AuthResponseDomain> {
        return try {
            // Регистрация
            val response = apiInterface.signUp(AuthRequest(email, password))  // AuthResponse

            if (response.accessToken == null) {
                return Rezult.Failure(Exception("Registration failed: No access token"))
            }

            val authData = AuthMapper.mapToDomain(response)  //AuthResponseDomain
            currentUser = User(
                id = authData.user.id,
                email = authData.user.email
            )

            // создание записи в таблице user
            apiInterface.createUser(response.user)
            Rezult.Success(authData)
        } catch (e: Exception) {
            Rezult.Failure(Exception("Registration failed: ${e.message}"))
        }
    }

    // метод авторизации пользователей
    override suspend fun signIn(
        email: String,
        password: String
    ): Rezult<AuthResponseDomain> {
        return try {
            // Аутентификация
            val response = apiInterface.signIn(AuthRequest(email, password))  // AuthResponse
            val authData = AuthMapper.mapToDomain(response)  //AuthResponseDomain

            currentUser = User(
                id = authData.user.id,
                email = authData.user.email
            )
            Rezult.Success(authData)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }

    override suspend fun getCurrentUser(): Rezult<User> {
        return try {
            val user = currentUser
            if (user != null) {
                Rezult.Success(user)
            } else {
                Rezult.Failure(Exception("войдите в систему или зарегистрируйтесь"))
            }
        } catch (e: Exception) {
            Rezult.Failure(Exception("ошибка получения пользователя ${e.message.toString()}"))
        }
    }
}