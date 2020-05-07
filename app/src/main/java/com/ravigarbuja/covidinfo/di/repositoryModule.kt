package com.ravigarbuja.covidinfo.di

import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.data.network.repository.CountryCasesRepository
import com.ravigarbuja.covidinfo.data.network.repository.CountryCasesRepositoryImpl
import com.ravigarbuja.covidinfo.data.network.repository.SummaryRepository
import com.ravigarbuja.covidinfo.data.network.repository.SummaryRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory { provideSummaryRepository(get(), get()) }
    factory { provideCountryCasesRepository(get()) }
}

fun provideSummaryRepository(
    apiServices: ApiServices,
    countryCode: CharSequence
): SummaryRepository {
    return SummaryRepositoryImpl(apiServices, countryCode)
}

fun provideCountryCasesRepository(
    apiServices: ApiServices
): CountryCasesRepository {
    return CountryCasesRepositoryImpl(apiServices)
}