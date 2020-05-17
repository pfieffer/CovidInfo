package com.ravigarbuja.covidinfo.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

interface BaseRepository {
    fun cancel()
}

abstract class BaseRepositoryImpl : BaseRepository {
    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    /**
     * This is the scope for all coroutines launched by [BaseViewModel].
     *
     * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
     * viewModelJob.cancel().  This is called in [BaseViewModel.onCleared].
     */
    val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun cancel() {
        viewModelJob.cancel()
    }
}