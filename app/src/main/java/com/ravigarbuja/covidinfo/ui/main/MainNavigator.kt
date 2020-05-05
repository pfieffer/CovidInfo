package com.ravigarbuja.covidinfo.ui.main

import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.data.model.Global

interface MainNavigator {
    fun navigateToSummaryDetail(global: Global, date: String)
    fun navigateToByCountriesScreen(countryList: ArrayList<Country>)
    fun navigateToMyCountryDetail(country: Country)
}