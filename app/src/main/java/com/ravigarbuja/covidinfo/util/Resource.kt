package com.ravigarbuja.covidinfo.util

import com.google.gson.annotations.SerializedName

/**
 *A generic class that contains data of type [T] and userStatus about loading this data.
 * loading : Api call is in progress
 * success : Api call success / finished
 * progress : Api call success and HTTP_SEE_OTHER
 * error : Api call cause error
 */
data class Resource<T>(
    @SerializedName("userStatus")
    val status: Status,
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("message")
    val message: String? = null
) {
    companion object {
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data = data, message = null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data = data, message = null)
        }

        fun <T> progress(message: String, data: T? = null): Resource<T> {
            return Resource(Status.PROGRESS, data = data, message = message)
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
    PROGRESS,
    ERROR;

    /**
     * Returns `true` if the [Status] is loading else `false`.
     */
    fun isLoading() = this == LOADING
}

