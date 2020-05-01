package com.ravigarbuja.covidinfo.di

import com.ravigarbuja.covidinfo.ui.countries.CountryListViewModel
import com.ravigarbuja.covidinfo.ui.main.MainViewModel
import com.ravigarbuja.covidinfo.ui.summary.SummaryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SummaryViewModel() }
    viewModel { CountryListViewModel() }
}