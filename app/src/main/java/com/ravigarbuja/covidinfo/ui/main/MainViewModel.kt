package com.ravigarbuja.covidinfo.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseViewModel
import com.ravigarbuja.covidinfo.data.network.model.Country
import com.ravigarbuja.covidinfo.data.network.model.Summary
import com.ravigarbuja.covidinfo.data.network.repository.SummaryRepository
import com.ravigarbuja.covidinfo.ui.main.MainNavigator
import com.ravigarbuja.covidinfo.util.Resource

class MainViewModel(
    private val summaryRepository: SummaryRepository
) : BaseViewModel<MainNavigator>() {
    val summaryLiveData = MediatorLiveData<Resource<Summary>>()

    init {
        summaryLiveData.addSource(summaryRepository.getSummary()) {
            summaryLiveData.value = it
        }
    }

    val summaryData = MutableLiveData<Summary>()
    val totalConfirmed = MutableLiveData<String>()
    val totalDeaths = MutableLiveData<String>()
    val totalRecovered = MutableLiveData<String>()

    fun populateData(data: Summary) {
        totalConfirmed.postValue(data.global.totalConfirmed.toString())
        totalDeaths.postValue(data.global.totalDeaths.toString())
        totalRecovered.postValue(data.global.totalRecovered.toString())
        summaryData.postValue(data)
    }

    fun onSummaryClicked(){
        getNavigator().navigateToSummaryDetail(summaryData.value!!.global, summaryData.value!!.date)
    }

    fun onByCountriesClicked(){
        getNavigator().navigateToByCountriesScreen(summaryData.value!!.countries as ArrayList<Country>)
    }

}