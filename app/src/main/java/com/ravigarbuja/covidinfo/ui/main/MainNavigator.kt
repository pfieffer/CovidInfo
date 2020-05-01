package com.ravigarbuja.covidinfo.ui.main

import com.ravigarbuja.covidinfo.data.network.model.Global

interface MainNavigator{
    fun navigateToSummaryDetail(global: Global, date: String)
    fun navigateToByCountriesScreen()
}