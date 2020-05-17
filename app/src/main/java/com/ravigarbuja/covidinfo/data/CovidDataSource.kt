package com.ravigarbuja.covidinfo.data

import androidx.lifecycle.LiveData
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.model.Summary
import com.ravigarbuja.covidinfo.util.Resource

interface CovidDataSource {
    suspend fun getSummaryData(): Summary
    suspend fun getTotalAllStatusCasesFromDayOne(countrySlug: String): List<DayCase>
}