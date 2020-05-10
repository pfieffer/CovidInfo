package com.ravigarbuja.covidinfo.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseDataStoreImpl
import com.ravigarbuja.covidinfo.data.CovidDataStore
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.model.Summary
import com.ravigarbuja.covidinfo.util.Resource
import kotlinx.coroutines.launch

class RemoteDataSource(private val covidApi: ApiServices) : BaseDataStoreImpl(), CovidDataStore {

    override fun getSummary(): LiveData<Resource<Summary>> {
        val response = MutableLiveData<Resource<Summary>>()
        viewModelScope.launch {
            try {
                response.postValue(Resource.loading(null))
                val res = covidApi.getSummaryAsync().await()
                // assign imagePath as it is not provided from the api
                for (country in res.countries) {
                    country.imagePath =
                        "countriesFlag/" + country.countryCode.toLowerCase() + ".png"
                }
                response.postValue(Resource.success(res))
            } catch (e: Throwable) {
                e.printStackTrace()
                response.postValue(Resource.error("Something went wrong"))
            }
        }
        return response
    }

    override fun getTotalAllStatusCasesFromDayOne(countrySlug: String): LiveData<Resource<List<DayCase>>> {
        val response = MutableLiveData<Resource<List<DayCase>>>()
        viewModelScope.launch {
            try {
                response.postValue(Resource.loading(null))
                val res = covidApi.getTotalCasesSinceDayOneAsync(countrySlug).await()
                response.postValue(Resource.success(res))
            } catch (e: Throwable){
                e.printStackTrace()
                response.postValue(Resource.error("Something went wrong"))
            }
        }
        return response
    }
}