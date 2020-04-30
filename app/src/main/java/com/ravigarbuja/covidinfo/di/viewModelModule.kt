package com.ravigarbuja.covidinfo.di

import com.ravigarbuja.covidinfo.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}