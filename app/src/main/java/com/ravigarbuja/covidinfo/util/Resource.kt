package com.ravigarbuja.covidinfo.util

/**
 *A generic class that contains data of type [T] and userStatus about loading this data.
 * loading : Api call is in progress
 * success : Api call success / finished
 * error : Api call cause error
 */
data class Resource<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data = data, message = null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data = data, message = null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data = data, message = message)
        }
    }
}

/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch userStatus.
 */
enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}
