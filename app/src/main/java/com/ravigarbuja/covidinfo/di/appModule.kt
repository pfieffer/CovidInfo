package com.ravigarbuja.covidinfo.di

import android.content.Context
import android.content.res.Resources
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.core.os.ConfigurationCompat
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.dsl.module
import java.util.*

/**
 * app module koin
 */
val appModule = module {
    single { provideResources(get()) }
    single { provideGson() }
    factory { CoroutineScope(Dispatchers.Main + Job()) }
    single { provideDefaultCountryCode(get()) }
}


fun provideResources(context: Context): Resources = context.resources

fun provideGson(): Gson = Gson()

fun provideDefaultCountryCode(context: Context): CharSequence {
    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    var countyCode = tm.networkCountryIso
    if (TextUtils.isEmpty(countyCode)) {
        countyCode = ConfigurationCompat.getLocales(context.resources.configuration).get(0).country
    }
    return countyCode.toUpperCase(Locale.getDefault())
}