package com.ravigarbuja.covidinfo.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Simple function to check if the network is available
 *
 * Requries <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> in
 * AndroidManifest.xml
 */
fun hasNetwork(context: Context): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}