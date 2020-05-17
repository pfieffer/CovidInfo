package com.ravigarbuja.covidinfo.di

import android.content.Context
import androidx.room.Room
import com.ravigarbuja.covidinfo.DB_NAME
import com.ravigarbuja.covidinfo.data.local.CovidInfoDB
import org.koin.dsl.module

val persistenceDataModule = module {
    single { provideRoomDatabase(get(), DB_NAME) }

    single(createdAtStart = false) { provideSummaryDao(get()) }
    single(createdAtStart = false) { provideDayCasesDao(get()) }
}

fun provideRoomDatabase(context: Context, dbName: String): CovidInfoDB {
    return Room.databaseBuilder(context, CovidInfoDB::class.java, dbName)
        .build()

}

fun provideSummaryDao(appDatabase: CovidInfoDB) = appDatabase.summaryDao()

fun provideDayCasesDao(appDatabase: CovidInfoDB) = appDatabase.dayCasesDao()