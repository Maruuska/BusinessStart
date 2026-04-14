package com.example.ppmob.data.remote

import com.example.ppmob.data.dto.ActivityDto
import com.example.ppmob.data.dto.AddressDto
import com.example.ppmob.data.dto.AuthRequest
import com.example.ppmob.data.dto.AuthResponse
import com.example.ppmob.data.dto.AuthUserDto
import com.example.ppmob.data.dto.BankDto
import com.example.ppmob.data.dto.CodeCountryDto
import com.example.ppmob.data.dto.CompanyDto
import com.example.ppmob.data.dto.CountyDto
import com.example.ppmob.data.dto.CreateDutyDto
import com.example.ppmob.data.dto.CreateRightDto
import com.example.ppmob.data.dto.DutyDto
import com.example.ppmob.data.dto.FormDto
import com.example.ppmob.data.dto.RightsDto
import com.example.ppmob.domain.model.Bank
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
    @POST("auth/v1/token?grant_type=password")
    suspend fun signIn(@Body authRequest: AuthRequest): AuthResponse

    // POST-запрос для регистрации
    @POST("auth/v1/signup")
    suspend fun signUp(@Body authRequest: AuthRequest): AuthResponse

    // POST-запрос для создания записи в таблице user
    @POST("rest/v1/users")
    suspend fun createUser(@Body user: AuthUserDto)

    // GET-запрос для получения списка адресов
    @GET("rest/v1/addresses")
    suspend fun getAddresses(): List<AddressDto>

    // GET-запрос для получения списка видов деятельности
    @GET("rest/v1/activitys")
    suspend fun getActivitys(): List<ActivityDto>

    // POST-запрос для создания записи в таблице companys
    @POST("rest/v1/companys")
    suspend fun createCompany(@Body company: CompanyDto)

    // GET-запрос для получения списка стран
    @GET("rest/v1/countries")
    suspend fun getCountries(): List<CountyDto>

    // GET-запрос для получения списка прав
    @GET("rest/v1/rights")
    suspend fun getRights(): List<RightsDto>

    // POST-запрос для создания записи в таблице права
    @POST("rest/v1/rights")
    suspend fun createRights(@Body right: CreateRightDto)

    // GET-запрос для получения списка обязанностей
    @GET("rest/v1/duties")
    suspend fun getDuties(): List<DutyDto>

    // POST-запрос для создания записи в таблице duties
    @POST("rest/v1/duties")
    suspend fun createDuties(@Body duty: CreateDutyDto)

    // GET-запрос для получения списка кодов стран
    @GET("rest/v1/code_countries")
    suspend fun getCodeCountries(): List<CodeCountryDto>

    // GET-запрос для получения списка форм
    @GET("rest/v1/forms")
    suspend fun getForms(): List<FormDto>

    // GET-запрос для получения списка банков
    @GET("rest/v1/banks")
    suspend fun getBanks(): List<BankDto>

}