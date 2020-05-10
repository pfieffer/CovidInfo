package com.ravigarbuja.covidinfo.data

import androidx.lifecycle.LiveData
import com.ravigarbuja.covidinfo.base.BaseDataStore
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.model.Summary
import com.ravigarbuja.covidinfo.util.Resource

interface CovidDataStore : BaseDataStore {
    fun getSummary(): LiveData<Resource<Summary>>
    fun getTotalAllStatusCasesFromDayOne(countrySlug: String): LiveData<Resource<List<DayCase>>>
}