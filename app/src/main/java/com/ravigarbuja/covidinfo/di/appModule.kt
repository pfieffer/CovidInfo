package com.ravigarbuja.covidinfo.di

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.dsl.module

/**
 * app module koin
 */
val appModule = module {
    single { provideResources(get()) }
    single { provideGson() }
    factory { CoroutineScope(Dispatchers.Main + Job()) }
}


fun provideResources(context: Context): Resources = context.resources

fun provideGson(): Gson = Gson()