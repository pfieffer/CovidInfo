package com.ravigarbuja.covidinfo.ui.summary

import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseViewModel
import com.ravigarbuja.covidinfo.data.network.model.Global

class SummaryViewModel: BaseViewModel<SummaryNavigator>(){

    val dateLiveData = MutableLiveData<String>()
    val globalLiveData = MutableLiveData<Global>()

    fun setGlobalData(global: Global) {
        globalLiveData.postValue(global)
    }

    fun setDate(date: String) {
        //todo: formatting date here
        dateLiveData.postValue(date)
    }

}