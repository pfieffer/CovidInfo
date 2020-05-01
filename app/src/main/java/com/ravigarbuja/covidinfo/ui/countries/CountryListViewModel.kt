package com.ravigarbuja.covidinfo.ui.countries

import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseViewModel
import com.ravigarbuja.covidinfo.data.network.model.Country
import com.ravigarbuja.covidinfo.util.SingleLiveEvent

class CountryListViewModel : BaseViewModel<CountryListNavigator>(){
    val countryList = MutableLiveData<List<Country>>()
    val countrySelectedEvent = SingleLiveEvent<Country>()

    fun onCountryItemSelected(countryModel: Country) {
        countrySelectedEvent.value = countryModel
    }
}