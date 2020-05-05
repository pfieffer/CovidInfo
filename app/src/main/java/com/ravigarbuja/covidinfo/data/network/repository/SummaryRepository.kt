package com.ravigarbuja.covidinfo.data.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseRepository
import com.ravigarbuja.covidinfo.base.BaseRepositoryImpl
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.data.model.Summary
import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.util.Resource
import kotlinx.coroutines.launch

interface SummaryRepository : BaseRepository {
    fun getSummary(): LiveData<Resource<Summary>>
    fun getDefaultCountry(): LiveData<Country>
}

class SummaryRepositoryImpl(
    private val apiService: ApiServices,
    private val defaultCountryCode: CharSequence
) : BaseRepositoryImpl(), SummaryRepository {
    private val defaultCountryMLD = MediatorLiveData<Country>()

    override fun getSummary(): LiveData<Resource<Summary>> {
        val response = MutableLiveData<Resource<Summary>>()
        viewModelScope.launch {
            try {
                response.postValue(Resource.loading(null))
                val res = apiService.getSummaryAsync().await()
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

    override fun getDefaultCountry(): LiveData<Country> {
        viewModelScope.launch {
            val res = apiService.getSummaryAsync().await()
            for (country in res.countries) {
                if (country.countryCode == defaultCountryCode) {
                    country.imagePath =
                        "countriesFlag/" + country.countryCode.toLowerCase() + ".png"
                    defaultCountryMLD.value = country
                    break
                }
            }
        }
        return defaultCountryMLD
    }
}