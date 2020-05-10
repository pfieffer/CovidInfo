package com.ravigarbuja.covidinfo.data.repository

import androidx.lifecycle.LiveData
import com.ravigarbuja.covidinfo.data.CovidDataStore
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.util.Resource

interface CountryCasesRepository {
    fun getTotalAllStatusCasesFromDayOne(countrySlug: String): LiveData<Resource<List<DayCase>>>
}

class CountryCasesRepositoryImpl(
    private val remoteDataSource: CovidDataStore
) : CountryCasesRepository {

    override fun getTotalAllStatusCasesFromDayOne(countrySlug: String): LiveData<Resource<List<DayCase>>> {
        return remoteDataSource.getTotalAllStatusCasesFromDayOne(countrySlug)
    }

}