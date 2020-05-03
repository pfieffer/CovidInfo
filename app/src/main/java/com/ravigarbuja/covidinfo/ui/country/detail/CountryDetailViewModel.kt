package com.ravigarbuja.covidinfo.ui.country.detail

import androidx.databinding.ObservableField
import com.ravigarbuja.covidinfo.base.BaseViewModel
import com.ravigarbuja.covidinfo.data.model.Country

class CountryDetailViewModel : BaseViewModel<CountryDetailNavigator>(){
    val currentCountry = ObservableField<Country>()

}