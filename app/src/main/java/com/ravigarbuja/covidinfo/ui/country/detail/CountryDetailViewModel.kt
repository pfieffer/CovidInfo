package com.ravigarbuja.covidinfo.ui.country.detail

import androidx.databinding.ObservableField
import androidx.lifecycle.MediatorLiveData
import com.ravigarbuja.covidinfo.base.BaseViewModel
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.repository.CountryCasesRepository
import com.ravigarbuja.covidinfo.util.Resource

class CountryDetailViewModel(private val countryCasesRepository: CountryCasesRepository) :
    BaseViewModel<CountryDetailNavigator>() {
    val currentCountry = ObservableField<Country>()
    val mortalityPercent = ObservableField<Float>()
    val recoveryPercent = ObservableField<Float>()

    val allCasesSinceDayOneMLD = MediatorLiveData<Resource<List<DayCase>>>()

    fun loadAllCasesDataSinceDayOne() {
        allCasesSinceDayOneMLD.addSource(
            countryCasesRepository.getTotalAllStatusCasesFromDayOne(currentCountry.get()!!.slug)
        ) {
            allCasesSinceDayOneMLD.value = it
        }
    }

    fun calcMortalityPercent(){
        val mortality = currentCountry.get()!!.totalDeaths.toFloat() / currentCountry.get()!!.totalConfirmed
        mortalityPercent.set(mortality)
    }

    fun calcRecoveryPercent() {
        val recovery = currentCountry.get()!!.totalRecovered.toFloat() / currentCountry.get()!!.totalConfirmed
        recoveryPercent.set(recovery)
    }
}