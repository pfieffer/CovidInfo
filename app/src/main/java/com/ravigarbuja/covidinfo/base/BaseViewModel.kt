package com.ravigarbuja.covidinfo.base

import androidx.lifecycle.ViewModel
import com.ravigarbuja.covidinfo.util.SingleLiveEvent

abstract class BaseViewModel<N>: ViewModel() {
    private var navigator: N? = null
    val collapseSoftKeyboard = SingleLiveEvent<Unit>()

    fun getNavigator() = navigator!!

    fun setNavigator(navigator: N) {
        this.navigator = navigator
    }
}