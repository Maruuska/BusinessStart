package com.example.ppmob.data.remote

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

// Интерфейс REST API Supabase
interface ApiInterface { // Объявление интерфейса для HTTP-запросов

    // POST-запрос для авторизации
//    @POST("auth/v1/token?grant_type=password")
//    suspend fun signIn(@Body authRequest: AuthRequest): AuthResponse
//
//    // POST-запрос для регистрации
//    @POST("auth/v1/signup")
//    suspend fun signUp(@Body authRequest: AuthRequest): AuthResponse

    // POST-запрос для создания записи в таблице user
//    @POST("rest/v1/user")
//    suspend fun createUser(@Body user: UserDto)

    // GET-запрос для получения пользователя по ID
//    @GET("rest/v1/user")
//    suspend fun getUserById(@Query("user_id") userId: String): List<UserDto>

    // GET-запрос для получения списка карточек
//    @GET("rest/v1/card")
//    suspend fun getCards(): List<CardDto>



//    // POST-запрос для загрузки картинки в бакет
//    @Multipart
//    @POST("storage/v1/object/pictures/{imageName}")
//    suspend fun addImageToBucket(@Path("imageName") imageName: String,/* @Body byteArray: ByteArray*/ @Part file: MultipartBody.Part)

}