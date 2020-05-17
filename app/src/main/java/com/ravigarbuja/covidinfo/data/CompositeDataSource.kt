package com.ravigarbuja.covidinfo.data

import com.ravigarbuja.covidinfo.data.local.LocalDataSource
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.model.Summary
import com.ravigarbuja.covidinfo.data.network.RemoteDataSource

class CompositeDataSource(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CovidDataSource {

    /**
     * If summary can be fetched from remote data source, do it and also save it to local db
     * after clearing the table
     * If it can not be fetched from remote data source, fetch it from local data source
     */
    override suspend fun getSummaryData(): Summary {
        return try {
            remoteDataSource.getSummaryAsync().await().also {
                localDataSource.clearSummaryData()
                localDataSource.insertSummaryData(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            localDataSource.getSummary()
        }
    }

    override suspend fun getTotalAllStatusCasesFromDayOne(countrySlug: String): List<DayCase> {
        return try {
            remoteDataSource.getTotalAllStatusCasesFromDayOneAsync(countrySlug).await().also {
//                localDataSource.clearDayCases()
                localDataSource.clearDayCasesFor(countrySlug)
                localDataSource.insertDayCases(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            localDataSource.getTotalAllStatusCasesFromDayOne(countrySlug)
        }
    }

}