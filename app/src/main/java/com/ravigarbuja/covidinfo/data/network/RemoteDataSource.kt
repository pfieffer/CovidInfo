package com.ravigarbuja.covidinfo.data.network

import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.model.Summary
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RemoteDataSource {
    suspend fun getSummaryAsync(): Deferred<Summary>
    suspend fun getTotalAllStatusCasesFromDayOneAsync(countrySlug: String): Deferred<List<DayCase>>
}

class RemoteDataSourceImpl(private val covidApi: ApiServices) :
    RemoteDataSource {

    override suspend fun getSummaryAsync(): Deferred<Summary> {
        return withContext(Dispatchers.IO) {
            covidApi.getSummaryAsync()
        }
    }

    override suspend fun getTotalAllStatusCasesFromDayOneAsync(countrySlug: String): Deferred<List<DayCase>> {
        return withContext(Dispatchers.IO) {
            covidApi.getTotalCaseSinceDayOneAsync(countrySlug)
        }
    }
}