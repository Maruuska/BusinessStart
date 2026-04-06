package com.example.ppmob.di

import com.example.ppmob.data.remote.ApiInterface
import com.example.ppmob.data.remote.ApiInterfaceService
import com.example.ppmob.data.repository.CompanyRepositoryImpl
import com.example.ppmob.data.repository.CountryRepositoryImpl
import com.example.ppmob.data.repository.RegulationRepositoryImpl
import com.example.ppmob.domain.repository.CompanyRepository
import com.example.ppmob.domain.repository.CountryRepository
import com.example.ppmob.domain.repository.RegulationRepository
import com.example.ppmob.domain.usecase.CreateCompanyUseCase
import com.example.ppmob.domain.usecase.CreateRightUseCase
import com.example.ppmob.domain.usecase.GetActivitysUseCase
import com.example.ppmob.domain.usecase.GetAddressUseCase
import com.example.ppmob.domain.usecase.GetCountriesUseCase
import com.example.ppmob.domain.usecase.GetRightsUseCase
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

    // реализация CountryRepository
    @Provides
    @Singleton
    fun provideCountryRepository(apiInterface: ApiInterface): CountryRepository {
        return CountryRepositoryImpl(apiInterface)
    }

    // реализация RegulationRepository
    @Provides
    @Singleton
    fun provideRegulationRepository(apiInterface: ApiInterface): RegulationRepository {
        return RegulationRepositoryImpl(apiInterface)
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

    @Provides
    @Singleton
    fun provideGetCountriesUseCase(countryRepository: CountryRepository): GetCountriesUseCase {
        return GetCountriesUseCase(countryRepository)
    }

    @Provides
    @Singleton
    fun provideGetRightsUseCase(regulationRepository: RegulationRepository): GetRightsUseCase {
        return GetRightsUseCase(regulationRepository)
    }

    @Provides
    @Singleton
    fun provideCreateRightUseCase(regulationRepository: RegulationRepository): CreateRightUseCase {
        return CreateRightUseCase(regulationRepository)
    }
}