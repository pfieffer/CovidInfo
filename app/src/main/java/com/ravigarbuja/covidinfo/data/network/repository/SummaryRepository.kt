package com.ravigarbuja.covidinfo.data.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseRepository
import com.ravigarbuja.covidinfo.base.BaseRepositoryImpl
import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.data.model.Summary
import com.ravigarbuja.covidinfo.util.Resource
import kotlinx.coroutines.launch

interface SummaryRepository : BaseRepository {
    fun getSummary(): LiveData<Resource<Summary>>
}

class SummaryRepositoryImpl(
    private val apiService: ApiServices
) : BaseRepositoryImpl(), SummaryRepository {

    override fun getSummary(): LiveData<Resource<Summary>> {
        val response = MutableLiveData<Resource<Summary>>()
        viewModelScope.launch {
            try {
                response.postValue(Resource.loading(null))
                val res = apiService.getSummaryAsync().await()
                response.postValue(Resource.success(res))
            } catch (e: Throwable) {
                e.printStackTrace()
                response.postValue(Resource.error("Something went wrong"))
            }
        }
        return response
    }

}