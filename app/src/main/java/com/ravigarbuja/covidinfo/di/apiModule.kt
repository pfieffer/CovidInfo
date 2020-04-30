package com.ravigarbuja.covidinfo.di

import com.google.gson.GsonBuilder
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.ravigarbuja.covidinfo.BASE_URL
import com.ravigarbuja.covidinfo.data.network.ApiServiceHolder
import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.util.CustomCoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Remote Web Service dataSource
 */
val apiModule = module {
    single { createOkHttpClient() }
    single { provideApiServiceHolder() }
    single { createWebService(get()) }
    single { provideApiService(get(), get()) }
}


fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(OkHttpProfilerInterceptor())
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createWebService(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .addCallAdapterFactory(CustomCoroutineCallAdapterFactory())
        .build()
}

fun provideApiService(retrofit: Retrofit, apiServiceHolder: ApiServiceHolder): ApiServices {
    val apiService = retrofit.create(ApiServices::class.java)
    apiServiceHolder.apiService = apiService
    return apiService
}

fun provideApiServiceHolder(): ApiServiceHolder = ApiServiceHolder()
