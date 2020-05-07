package com.ravigarbuja.covidinfo.ui.country.detail

import androidx.databinding.ObservableField
import androidx.lifecycle.MediatorLiveData
import com.ravigarbuja.covidinfo.base.BaseViewModel
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.network.repository.CountryCasesRepository
import com.ravigarbuja.covidinfo.util.Resource

class CountryDetailViewModel(private val countryCasesRepository: CountryCasesRepository) :
    BaseViewModel<CountryDetailNavigator>() {
    val currentCountry = ObservableField<Country>()

    val allCasesSinceDayOneMLD = MediatorLiveData<Resource<List<DayCase>>>()

    fun loadAllCasesDataSinceDayOne() {
        allCasesSinceDayOneMLD.addSource(
            countryCasesRepository.getTotalAllStatusCasesFromDayOne(currentCountry.get()!!.slug)
        ) {
            allCasesSinceDayOneMLD.value = it
        }
    }
}