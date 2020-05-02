package com.ravigarbuja.covidinfo.data.network.repository

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ravigarbuja.covidinfo.base.BaseRepository
import com.ravigarbuja.covidinfo.base.BaseRepositoryImpl
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.data.model.Summary
import com.ravigarbuja.covidinfo.data.network.ApiServices
import com.ravigarbuja.covidinfo.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

interface SummaryRepository : BaseRepository {
    fun getSummary(): LiveData<Resource<Summary>>
    fun getDefaultCountry(): LiveData<Country>
}

class SummaryRepositoryImpl(
    private val apiService: ApiServices,
    private val resources: Resources,
    private val gson: Gson,
    private val defaultCountryCode: CharSequence
) : BaseRepositoryImpl(), SummaryRepository {
    private val defaultCountryMLD = MediatorLiveData<Country>()

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

    override fun getDefaultCountry(): LiveData<Country> {
        setCountryLiveData()
        return defaultCountryMLD
    }

    private fun setCountryLiveData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                for (country in loadCountryListFromResource()) {
                    if (country.countryCode == defaultCountryCode) {
                        defaultCountryMLD.value = country
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun loadCountryListFromResource(): List<Country> {
        return withContext(Dispatchers.IO) {
            val countries = ArrayList<Country>()
            val json: String
            try {
                val inputStream = resources.assets.open("Countries.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, StandardCharsets.UTF_8)
                val type = object : TypeToken<List<Country>>() {
                }.type
                countries.clear()
                countries.addAll(gson.fromJson(json, type))

                countries.forEach {
                    it.imagePath = "countriesFlag/" + it.countryCode.toLowerCase() + ".png"
                }
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            countries
        }
    }

}