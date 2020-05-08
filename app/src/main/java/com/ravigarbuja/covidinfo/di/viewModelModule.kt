package com.ravigarbuja.covidinfo.di

import com.ravigarbuja.covidinfo.ui.country.detail.CountryDetailViewModel
import com.ravigarbuja.covidinfo.ui.country.list.CountryListViewModel
import com.ravigarbuja.covidinfo.ui.main.MainViewModel
import com.ravigarbuja.covidinfo.ui.summary.SummaryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SummaryViewModel() }
    viewModel { CountryListViewModel() }
    viewModel { CountryDetailViewModel(get()) }
}