package com.ravigarbuja.covidinfo.ui.country.list

import com.ravigarbuja.covidinfo.data.model.Country

interface CountryListNavigator {
    fun onItemClick(country: Country)
}