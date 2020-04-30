package com.ravigarbuja.covidinfo.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.ravigarbuja.covidinfo.data.network.model.Summary
import com.ravigarbuja.covidinfo.data.network.repository.SummaryRepository
import com.ravigarbuja.covidinfo.util.Resource

class MainViewModel(
    private val summaryRepository: SummaryRepository
) : ViewModel() {
    val summaryLiveData = MediatorLiveData<Resource<Summary>>()

    init {
        summaryLiveData.addSource(summaryRepository.getSummary()) {
            summaryLiveData.value = it
        }
    }
}