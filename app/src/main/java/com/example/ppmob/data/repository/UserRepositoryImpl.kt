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
            val response = apiInterface.signUp(AuthRequest(email, password))

            if (response.accessToken == null) {
                return Rezult.Failure(Exception("Registration failed: No access token"))
            }

            val authData = AuthMapper.mapToDomain(response)
            currentUser = User(
                id = authData.user.id,
                email = authData.user.email
            )

            // создание записи в таблице user
            apiInterface.createUser(response.user)
            Rezult.Success(authData)
        } catch (e: retrofit2.HttpException) {
            // Обработка HTTP ошибок
            when (e.code()) {
                409 -> Rezult.Failure(Exception("Некорректный формат email или пароля"))
                422 -> Rezult.Failure(Exception("Пользователь с таким email уже существует"))
                400 -> Rezult.Failure(Exception("Неверный запрос. Проверьте введенные данные"))
                else -> Rezult.Failure(Exception("Ошибка сервера: ${e.message()}"))
            }
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
        } catch (e: retrofit2.HttpException) {
            // Обработка HTTP ошибок
            when (e.code()) {
                409 -> Rezult.Failure(Exception("Некорректный формат email или пароля"))
                422 -> Rezult.Failure(Exception("Пользователь с таким email уже существует"))
                400 -> Rezult.Failure(Exception("Неверный запрос. Проверьте введенные данные"))
                else -> Rezult.Failure(Exception("Ошибка сервера: ${e.message()}"))
            }
        } catch (e: Exception) {
            Rezult.Failure(Exception("Authorization failed: ${e.message}"))
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