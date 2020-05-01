package com.ravigarbuja.covidinfo.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravigarbuja.covidinfo.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseViewModel<N>: ViewModel() {
    private var navigator: N? = null
    val collapseSoftKeyboard = SingleLiveEvent<Unit>()

    fun getNavigator() = navigator!!

    fun setNavigator(navigator: N) {
        this.navigator = navigator
    }

    private var job: Job? = null
    fun <T : Any?> launchWithDebounce(
        coroutineScope: CoroutineScope = viewModelScope,
        debounceTimeInMillis: Long = 400,
        codeBlock: suspend CoroutineScope.() -> T
    ) {
        job?.cancel()
        val currentJob = coroutineScope.launch {
            // add delay from second request
            if (job != null) {
                delay(debounceTimeInMillis)
            }
            codeBlock()
        }
        job = currentJob
    }
}