package com.ravigarbuja.covidinfo.ui.country.list

import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseViewModel
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.ui.country.list.CountryListNavigator
import com.ravigarbuja.covidinfo.util.SingleLiveEvent

class CountryListViewModel : BaseViewModel<CountryListNavigator>(){
    val countryList = MutableLiveData<List<Country>>()
    val countrySelectedEvent = SingleLiveEvent<Country>()

    fun onCountryItemSelected(countryModel: Country) {
        countrySelectedEvent.value = countryModel
    }
}