package com.ravigarbuja.covidinfo.di

import com.ravigarbuja.covidinfo.data.CompositeDataSource
import com.ravigarbuja.covidinfo.data.CovidDataSource
import com.ravigarbuja.covidinfo.data.local.LocalDataSource
import com.ravigarbuja.covidinfo.data.local.LocalDataSourceImpl
import com.ravigarbuja.covidinfo.data.local.dao.DayCasesDao
import com.ravigarbuja.covidinfo.data.local.dao.SummaryDao
import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.data.network.RemoteDataSource
import com.ravigarbuja.covidinfo.data.network.RemoteDataSourceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataStoreModule = module {
    single(named("remote")) { provideRemoteDataStore(get()) }

    single(named("local")) { provideLocalDataStore(get(), get()) }

    single(named("composite")) {
        provideCompositeDataSource(
            get(qualifier = named("remote")),
            get(qualifier = named("local"))
        )
    }

}

fun provideRemoteDataStore(apiServices: ApiServices): RemoteDataSource {
    return RemoteDataSourceImpl(apiServices)
}


fun provideLocalDataStore(summaryDao: SummaryDao, dayCasesDao: DayCasesDao): LocalDataSource {
    return LocalDataSourceImpl(summaryDao, dayCasesDao)
}

fun provideCompositeDataSource(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
): CovidDataSource {
    return CompositeDataSource(remoteDataSource, localDataSource)
}