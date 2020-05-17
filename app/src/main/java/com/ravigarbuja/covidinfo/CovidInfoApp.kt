package com.ravigarbuja.covidinfo

import android.app.Application
import com.ravigarbuja.covidinfo.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CovidInfoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@CovidInfoApp)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            modules(
                listOf(
                    appModule,
                    apiModule,
                    persistenceDataModule,
                    dataStoreModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }


}