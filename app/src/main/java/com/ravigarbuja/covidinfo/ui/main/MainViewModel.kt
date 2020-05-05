package com.ravigarbuja.covidinfo.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseViewModel
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.data.model.Summary
import com.ravigarbuja.covidinfo.data.network.repository.SummaryRepository
import com.ravigarbuja.covidinfo.util.Resource

class MainViewModel(
    private val summaryRepository: SummaryRepository
) : BaseViewModel<MainNavigator>() {
    val dataLoaded = MutableLiveData<Boolean>(true)
    val summaryLiveData = MediatorLiveData<Resource<Summary>>()
    val defaultCountryLD = MediatorLiveData<Country>()

    init {
        loadSummaryData()
        setDefaultCountry()
    }

    private fun setDefaultCountry() {
        defaultCountryLD.addSource(summaryRepository.getDefaultCountry()) {
            defaultCountryLD.value = it
        }
    }

    private fun loadSummaryData() {
        summaryLiveData.addSource(summaryRepository.getSummary()) {
            summaryLiveData.value = it
        }
    }

    val summaryData = MutableLiveData<Summary>()
    val totalConfirmed = MutableLiveData<String>()
    val totalDeaths = MutableLiveData<String>()
    val totalRecovered = MutableLiveData<String>()

    fun populateData(data: Summary) {
        dataLoaded.postValue(true)
        totalConfirmed.postValue(data.global.totalConfirmed.toString())
        totalDeaths.postValue(data.global.totalDeaths.toString())
        totalRecovered.postValue(data.global.totalRecovered.toString())
        summaryData.postValue(data)
    }

    fun onSummaryClicked() {
        summaryData.value?.let {
            getNavigator().navigateToSummaryDetail(it.global, it.date)
        }
    }

    fun onByCountriesClicked() {
        summaryData.value?.let {
            getNavigator().navigateToByCountriesScreen(it.countries as ArrayList<Country>)
        }
    }

    fun onMyCountryClicked() {
        defaultCountryLD.value?.let {
            getNavigator().navigateToMyCountryDetail(it)
        }
    }

    fun onRetryClicked() {
        loadSummaryData()
    }

}