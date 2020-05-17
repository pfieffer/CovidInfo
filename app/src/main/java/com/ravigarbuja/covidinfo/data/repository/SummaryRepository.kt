package com.ravigarbuja.covidinfo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseRepository
import com.ravigarbuja.covidinfo.base.BaseRepositoryImpl
import com.ravigarbuja.covidinfo.data.CovidDataSource
import com.ravigarbuja.covidinfo.data.local.LocalDataSource
import com.ravigarbuja.covidinfo.data.model.Summary
import com.ravigarbuja.covidinfo.data.network.RemoteDataSource
import com.ravigarbuja.covidinfo.util.Resource
import kotlinx.coroutines.launch

interface SummaryRepository : BaseRepository {
    fun getSummary(): LiveData<Resource<Summary>>
}

class SummaryRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val covidDataSource: CovidDataSource
) : BaseRepositoryImpl(), SummaryRepository {

    override fun getSummary(): LiveData<Resource<Summary>> {
        val mld = MutableLiveData<Resource<Summary>>()
        viewModelScope.launch {
            try {
                mld.postValue(Resource.loading())
                val summary = covidDataSource.getSummaryData()
                for (country in summary.countries) {
                    country.imagePath =
                        "countriesFlag/" + country.countryCode.toLowerCase() + ".png"
                }
                mld.postValue(Resource.success(summary))
            } catch (e: Exception) {
                e.printStackTrace()
                mld.postValue(Resource.error(e.message!!))
            }
        }
        return mld
    }
}