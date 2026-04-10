package com.example.ppmob.di

import com.example.ppmob.data.remote.ApiInterface
import com.example.ppmob.data.remote.ApiInterfaceService
import com.example.ppmob.data.repository.BankRepositoryImpl
import com.example.ppmob.data.repository.CompanyRepositoryImpl
import com.example.ppmob.data.repository.CountryRepositoryImpl
import com.example.ppmob.data.repository.RegulationRepositoryImpl
import com.example.ppmob.data.repository.StatementRepositoryImpl
import com.example.ppmob.domain.repository.BankRepository
import com.example.ppmob.domain.repository.CompanyRepository
import com.example.ppmob.domain.repository.CountryRepository
import com.example.ppmob.domain.repository.RegulationRepository
import com.example.ppmob.domain.repository.StatementRepository
import com.example.ppmob.domain.usecase.CreateCompanyUseCase
import com.example.ppmob.domain.usecase.CreateDutyUseCase
import com.example.ppmob.domain.usecase.CreateRightUseCase
import com.example.ppmob.domain.usecase.GetActivitysUseCase
import com.example.ppmob.domain.usecase.GetAddressUseCase
import com.example.ppmob.domain.usecase.GetBanksUseCase
import com.example.ppmob.domain.usecase.GetCodeCountryUseCase
import com.example.ppmob.domain.usecase.GetCountriesUseCase
import com.example.ppmob.domain.usecase.GetDutiesUseCase
import com.example.ppmob.domain.usecase.GetFormsUseCase
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

    // реализация StatementRepository
    @Provides
    @Singleton
    fun provideStatementRepository(apiInterface: ApiInterface): StatementRepository {
        return StatementRepositoryImpl(apiInterface)
    }

    // реализация BankRepository
    @Provides
    @Singleton
    fun provideBankRepository(apiInterface: ApiInterface): BankRepository {
        return BankRepositoryImpl(apiInterface)
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

    @Provides
    @Singleton
    fun provideGetDutiesUseCase(regulationRepository: RegulationRepository): GetDutiesUseCase {
        return GetDutiesUseCase(regulationRepository)
    }

    @Provides
    @Singleton
    fun provideCreateDutyUseCase(regulationRepository: RegulationRepository): CreateDutyUseCase {
        return CreateDutyUseCase(regulationRepository)
    }

    @Provides
    @Singleton
    fun provideGetCodeCountryUseCase(statementRepository: StatementRepository): GetCodeCountryUseCase {
        return GetCodeCountryUseCase(statementRepository)
    }

    @Provides
    @Singleton
    fun provideGetFormsUseCase(statementRepository: StatementRepository): GetFormsUseCase {
        return GetFormsUseCase(statementRepository)
    }

    @Provides
    @Singleton
    fun provideGetBanksUseCase(bankRepository: BankRepository): GetBanksUseCase {
        return GetBanksUseCase(bankRepository)
    }
}