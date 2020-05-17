package com.ravigarbuja.covidinfo.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.ravigarbuja.covidinfo.*
import com.ravigarbuja.covidinfo.data.network.ApiServiceHolder
import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.util.CustomCoroutineCallAdapterFactory
import com.ravigarbuja.covidinfo.util.hasNetwork
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Remote Web Service dataSource
 */
val apiModule = module {
    single { createOkHttpClient(androidContext()) }
    single { provideApiServiceHolder() }
    single { createWebService(get()) }
    single { provideApiService(get(), get()) }
}


fun createOkHttpClient(context: Context): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
//        .cache(Cache(context.cacheDir, MAX_CACHE_SIZE))
        .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
//        .addInterceptor(object : Interceptor {
//            override fun intercept(chain: Interceptor.Chain): Response {
//                var request: Request = chain.request()
//                if (hasNetwork((context)) != null) {
//                    if (!hasNetwork(context)!!) {
//                        val maxStale = STALE_CACHE_TOLERANCE_SECONDS
//                        request = request
//                            .newBuilder()
//                            .header(
//                                "Cache-Control",
//                                "private, If-Not-Modified, max-stale=$maxStale"
//                            )
//                            .build()
//                    }
//                }
//                return chain.proceed(request)
//
//            }
//
//        })

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
