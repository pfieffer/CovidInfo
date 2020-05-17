package com.ravigarbuja.covidinfo.di

import com.ravigarbuja.covidinfo.data.CovidDataSource
import com.ravigarbuja.covidinfo.data.local.LocalDataSource
import com.ravigarbuja.covidinfo.data.network.RemoteDataSource
import com.ravigarbuja.covidinfo.data.repository.CountryCasesRepository
import com.ravigarbuja.covidinfo.data.repository.CountryCasesRepositoryImpl
import com.ravigarbuja.covidinfo.data.repository.SummaryRepository
import com.ravigarbuja.covidinfo.data.repository.SummaryRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        provideSummaryRepository(
            get(qualifier = named("remote")),
            get(qualifier = named("local")),
            get(qualifier = named("composite"))
        )
    }
    factory {
        provideCountryCasesRepository(
            get(qualifier = named("remote")),
            get(qualifier = named("local")),
            get(qualifier = named("composite"))
        )
    }
}

fun provideSummaryRepository(
    remoteDataSource: RemoteDataSource,
    localDataStore: LocalDataSource,
    covidDataSource: CovidDataSource
): SummaryRepository {
    return SummaryRepositoryImpl(localDataStore, remoteDataSource, covidDataSource)
}

fun provideCountryCasesRepository(
    remoteDataSource: RemoteDataSource,
    localDataStore: LocalDataSource,
    covidDataSource: CovidDataSource
): CountryCasesRepository {
    return CountryCasesRepositoryImpl(localDataStore, remoteDataSource, covidDataSource)
}