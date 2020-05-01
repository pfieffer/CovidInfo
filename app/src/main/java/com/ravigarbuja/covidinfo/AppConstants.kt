package com.ravigarbuja.covidinfo

const val BASE_URL = "https://api.covid19api.com/"

class Api {
    object EndPoint {
        const val SUMMARY = "summary"
    }
}

const val INTENT_EXTRA_GLOBAL_DATA = "global"
const val INTENT_EXTRA_DATE_DATA = "date"
const val INTENT_EXTRA_COUNTRIES_DATA = "countries-data"
const val INTENT_EXTRA_COUNTRY_DATA = "country"