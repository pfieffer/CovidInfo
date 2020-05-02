package com.ravigarbuja.covidinfo.ui.summary

import androidx.lifecycle.MutableLiveData
import com.ravigarbuja.covidinfo.base.BaseViewModel
import com.ravigarbuja.covidinfo.data.model.Global
import com.ravigarbuja.covidinfo.util.DateTimeUtils

class SummaryViewModel : BaseViewModel<SummaryNavigator>() {

    val globalLiveData = MutableLiveData<Global>()
    val dateLiveData = MutableLiveData<String>()
    val timeLiveData = MutableLiveData<String>()

    fun setGlobalData(global: Global) {
        globalLiveData.postValue(global)
    }

    fun setDateTime(dateTimeString: String) {
        val dateString = DateTimeUtils.getReadableDate(dateTimeString)
        dateLiveData.postValue(dateString)
        val timeString = DateTimeUtils.getReadableTime(dateTimeString)
        timeLiveData.postValue(timeString)
    }

}