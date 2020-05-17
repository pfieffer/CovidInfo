package com.ravigarbuja.covidinfo.data.local

import com.ravigarbuja.covidinfo.data.local.dao.DayCasesDao
import com.ravigarbuja.covidinfo.data.local.dao.SummaryDao
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.model.Summary

interface LocalDataSource {
    suspend fun insertSummaryData(summary: Summary)
    suspend fun clearSummaryData()
    suspend fun getSummary(): Summary

    suspend fun clearDayCases()
    suspend fun clearDayCasesFor(countrySlug: String)
    suspend fun insertDayCases(dayCases: List<DayCase>)
    suspend fun getTotalAllStatusCasesFromDayOne(countrySlug: String): List<DayCase>
}

class LocalDataSourceImpl(
    private val summaryDao: SummaryDao,
    private val dayCasesDao: DayCasesDao
) : LocalDataSource {

    override suspend fun insertSummaryData(summary: Summary) {
        summaryDao.insertSummary(summary)
    }

    override suspend fun clearSummaryData() {
        summaryDao.clear()
    }

    override suspend fun getSummary(): Summary {
        return summaryDao.getSummary()
    }

    override suspend fun clearDayCases() {
        dayCasesDao.clear()
    }

    override suspend fun clearDayCasesFor(countrySlug: String) {
        val summary = summaryDao.getSummary()

        val country = summary.countries.run {
            first {
                countrySlug == it.slug
            }
        }
        dayCasesDao.clearDayCasesDataFor(country.countryCode)
    }

    override suspend fun insertDayCases(dayCases: List<DayCase>) {
        dayCasesDao.insertDayCases(dayCases)
    }

    override suspend fun getTotalAllStatusCasesFromDayOne(countrySlug: String): List<DayCase> {
        val countries = summaryDao.getSummary().countries

        val country = countries.run {
            first {
                it.slug == countrySlug
            }
        }

        return dayCasesDao.getDayCasesFor(country.countryCode)
    }
}