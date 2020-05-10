package com.ravigarbuja.covidinfo.di

import com.ravigarbuja.covidinfo.data.CovidDataStore
import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.data.network.RemoteDataSource
import org.koin.dsl.module

val dataStoreModule = module {
    factory { provideRemoteDataStore(get()) } //todo: is factory the right choice here.
}

fun provideRemoteDataStore(apiServices: ApiServices): CovidDataStore {
    return RemoteDataSource(apiServices)
}
