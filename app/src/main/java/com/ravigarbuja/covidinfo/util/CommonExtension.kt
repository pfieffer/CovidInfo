package com.ravigarbuja.covidinfo.util

import java.text.NumberFormat
import java.text.ParseException

/**
 * Extension function to format an Int based on the locale and
 * return a formatted string
 */
fun Int.formatNumberBasedOnLocale(): String {
    val numberFormat = NumberFormat.getInstance()
    var parsedQty: Number? = null
    try {
        parsedQty = numberFormat.parse(this.toString())
    } catch (ex: ParseException) {
        ex.printStackTrace()
    }

    return if (parsedQty != null) {
        numberFormat.format(parsedQty)
    } else {
        "-1"
    }

}