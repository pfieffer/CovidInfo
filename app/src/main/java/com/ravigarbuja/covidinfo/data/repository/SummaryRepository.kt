package com.ravigarbuja.covidinfo.data.repository

import androidx.lifecycle.LiveData
import com.ravigarbuja.covidinfo.data.CovidDataStore
import com.ravigarbuja.covidinfo.data.model.Summary
import com.ravigarbuja.covidinfo.util.Resource

interface SummaryRepository {
    fun getSummary(): LiveData<Resource<Summary>>
}

class SummaryRepositoryImpl(
    private val remoteDataStore: CovidDataStore
) : SummaryRepository {

    override fun getSummary(): LiveData<Resource<Summary>> {
        return remoteDataStore.getSummary()
    }
}