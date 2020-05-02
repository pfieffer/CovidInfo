package com.ravigarbuja.covidinfo.data.network

import com.ravigarbuja.covidinfo.Api
import com.ravigarbuja.covidinfo.data.model.Summary
import kotlinx.coroutines.Deferred
import retrofit2.http.GET


interface ApiServices {
    @GET(Api.EndPoint.SUMMARY)
    fun getSummaryAsync(): Deferred<Summary>
}

