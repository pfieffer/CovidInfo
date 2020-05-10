package com.ravigarbuja.covidinfo.di

import com.ravigarbuja.covidinfo.data.CovidDataStore
import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.data.repository.CountryCasesRepository
import com.ravigarbuja.covidinfo.data.repository.CountryCasesRepositoryImpl
import com.ravigarbuja.covidinfo.data.repository.SummaryRepository
import com.ravigarbuja.covidinfo.data.repository.SummaryRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory { provideSummaryRepository(get()) }
    factory { provideCountryCasesRepository(get()) }
}

fun provideSummaryRepository(
    covidDataStore: CovidDataStore
): SummaryRepository {
    return SummaryRepositoryImpl(covidDataStore)
}

fun provideCountryCasesRepository(
    covidDataStore: CovidDataStore
): CountryCasesRepository {
    return CountryCasesRepositoryImpl(covidDataStore)
}