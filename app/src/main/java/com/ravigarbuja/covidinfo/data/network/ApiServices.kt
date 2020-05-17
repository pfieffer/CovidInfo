package com.ravigarbuja.covidinfo.data.network

import com.ravigarbuja.covidinfo.Api
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.model.Summary
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiServices {
    @GET(Api.EndPoint.SUMMARY)
    fun getSummaryAsync(): Deferred<Summary>

    @GET(Api.EndPoint.TOTAL_CASES_COUNTRY_DAY_ONE)
    fun getTotalCaseSinceDayOneAsync(@Path("country-slug") countrySlug: String): Deferred<List<DayCase>>
}

