package com.ravigarbuja.covidinfo.data.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseRepository
import com.ravigarbuja.covidinfo.base.BaseRepositoryImpl
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.util.Resource
import kotlinx.coroutines.launch

interface CountryCasesRepository: BaseRepository {
    fun getTotalAllStatusCasesFromDayOne(countrySlug: String): LiveData<Resource<List<DayCase>>>
}

class CountryCasesRepositoryImpl(
    private val apiService: ApiServices
) : BaseRepositoryImpl(), CountryCasesRepository {

    override fun getTotalAllStatusCasesFromDayOne(countrySlug: String): LiveData<Resource<List<DayCase>>> {
        val response = MutableLiveData<Resource<List<DayCase>>>()
        viewModelScope.launch {
            try {
                response.postValue(Resource.loading(null))
                val res = apiService.getTotalCasesSinceDayOneAsync(countrySlug).await()
                response.postValue(Resource.success(res))
            } catch (e: Throwable){
                e.printStackTrace()
                response.postValue(Resource.error("Something went wrong"))
            }
        }
        return response
    }

}