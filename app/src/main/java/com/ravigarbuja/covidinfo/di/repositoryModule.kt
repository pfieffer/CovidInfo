package com.ravigarbuja.covidinfo.di

import android.content.res.Resources
import com.google.gson.Gson
import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.data.network.repository.SummaryRepository
import com.ravigarbuja.covidinfo.data.network.repository.SummaryRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory { provideSummaryRepository(get(), get(), get(), get()) }
}

fun provideSummaryRepository(
    apiServices: ApiServices,
    resources: Resources,
    gson: Gson,
    countryCode: CharSequence
): SummaryRepository {
    return SummaryRepositoryImpl(apiServices, resources, gson, countryCode)
}