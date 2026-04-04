package com.example.ppmob.di

import com.example.ppmob.data.remote.ApiInterface
import com.example.ppmob.data.remote.ApiInterfaceService
import com.example.ppmob.data.repository.CompanyRepositoryImpl
import com.example.ppmob.domain.repository.CompanyRepository
import com.example.ppmob.domain.usecase.CreateCompanyUseCase
import com.example.ppmob.domain.usecase.GetActivitysUseCase
import com.example.ppmob.domain.usecase.GetAddressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Провайдер для ApiInterface
    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {
        return ApiInterfaceService.create()
    }

    // реализация CompanyRepository
    @Provides
    @Singleton
    fun provideCompanyRepository(apiInterface: ApiInterface): CompanyRepository{
        return CompanyRepositoryImpl(apiInterface)
    }


    // Предоставляет UseCase по созданию компании
    @Provides
    @Singleton
    fun provideCreateCompanyUseCase(companyRepository: CompanyRepository): CreateCompanyUseCase {
        return CreateCompanyUseCase(companyRepository)
    }

    @Provides
    @Singleton
    fun provideGetActivityUseCase(companyRepository: CompanyRepository): GetActivitysUseCase {
        return GetActivitysUseCase(companyRepository)
    }

    @Provides
    @Singleton
    fun provideGetAddressUseCase(companyRepository: CompanyRepository): GetAddressUseCase {
        return GetAddressUseCase(companyRepository)
    }
}