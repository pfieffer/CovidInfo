package com.ravigarbuja.covidinfo.di

import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.data.network.repository.SummaryRepository
import com.ravigarbuja.covidinfo.data.network.repository.SummaryRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory { provideSummaryRepository(get()) }
}

fun provideSummaryRepository(
    apiServices: ApiServices
): SummaryRepository {
    return SummaryRepositoryImpl(apiServices)
}