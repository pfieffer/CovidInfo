package com.ravigarbuja.covidinfo.ui.countries

import com.ravigarbuja.covidinfo.data.network.model.Country

interface CountryListNavigator {
    fun onItemClick(country: Country)
}