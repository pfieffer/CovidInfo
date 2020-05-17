package com.ravigarbuja.covidinfo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseRepository
import com.ravigarbuja.covidinfo.base.BaseRepositoryImpl
import com.ravigarbuja.covidinfo.data.CovidDataSource
import com.ravigarbuja.covidinfo.data.local.LocalDataSource
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.network.RemoteDataSource
import com.ravigarbuja.covidinfo.util.Resource
import kotlinx.coroutines.launch

interface CountryCasesRepository : BaseRepository {
    fun getTotalAllStatusCasesFromDayOne(countrySlug: String): LiveData<Resource<List<DayCase>>>
}

class CountryCasesRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val covidDataSource: CovidDataSource
) : BaseRepositoryImpl(), CountryCasesRepository {

    override fun getTotalAllStatusCasesFromDayOne(countrySlug: String): LiveData<Resource<List<DayCase>>> {
        val mld = MutableLiveData<Resource<List<DayCase>>>()
        viewModelScope.launch {
            try {
                mld.postValue(Resource.loading())
                val dayCases = covidDataSource.getTotalAllStatusCasesFromDayOne(countrySlug)
                mld.postValue(Resource.success(dayCases))
            } catch (e: Exception) {
                mld.postValue(Resource.error(e.message!!))
            }
        }
        return mld
    }

}