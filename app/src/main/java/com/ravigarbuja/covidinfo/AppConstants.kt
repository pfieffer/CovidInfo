package com.ravigarbuja.covidinfo

const val BASE_URL = "https://api.covid19api.com/"

class Api {
    object EndPoint {
        const val SUMMARY = "summary"
        const val TOTAL_CASES_COUNTRY_DAY_ONE = "total/dayone/country/{country-slug}"
    }
}

const val MAX_CACHE_SIZE = (5 * 1024 * 1024L) //5MB
const val CONNECT_TIMEOUT_SECONDS = 60L
const val READ_TIMEOUT_SECONDS = 60L
const val STALE_CACHE_TOLERANCE_SECONDS = (60 * 60 * 24 * 1) // tolerate 1 day stale

const val INTENT_EXTRA_GLOBAL_DATA = "global"
const val INTENT_EXTRA_DATE_DATA = "date"
const val INTENT_EXTRA_COUNTRIES_DATA = "countries-data"
const val INTENT_EXTRA_COUNTRY_DATA = "country"

const val DATE_TIME_FORMAT_API =
    "yyyy-MM-dd'T'HH:mm:ss" //2020-04-05T17:37:00Z ISO_FORMAT (UTC time)
const val FRIENDLY_DATE_FORMAT = "E MMM dd yyyy" //Fri Apr 05 2020
const val FRIENDLY_TIME_FORMAT = "h:mm a" //5:37 PM