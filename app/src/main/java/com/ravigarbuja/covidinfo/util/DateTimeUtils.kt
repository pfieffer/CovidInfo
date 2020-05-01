package com.ravigarbuja.covidinfo.util

import com.ravigarbuja.covidinfo.DATE_TIME_FORMAT_API
import com.ravigarbuja.covidinfo.FRIENDLY_DATE_FORMAT
import com.ravigarbuja.covidinfo.FRIENDLY_TIME_FORMAT
import okhttp3.internal.UTC
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    fun getReadableDate(dateTimeS: String): String {
        val formatter = SimpleDateFormat(DATE_TIME_FORMAT_API, Locale.getDefault())
        formatter.timeZone = UTC
        val dateTime: Date?
        try {
            dateTime = formatter.parse(dateTimeS)
        } catch (ex: ParseException){
            ex.printStackTrace()
            return ""
        }

        val friendlyDateFormat = SimpleDateFormat(FRIENDLY_DATE_FORMAT, Locale.getDefault())

        return if (dateTime != null)
            friendlyDateFormat.format(dateTime)
        else
            ""
    }


    fun getReadableTime(dateTimeS: String): String {
        val formatter = SimpleDateFormat(DATE_TIME_FORMAT_API, Locale.getDefault())
        formatter.timeZone = UTC
        val dateTime: Date?
        try {
            dateTime = formatter.parse(dateTimeS)
        } catch (ex: ParseException){
            ex.printStackTrace()
            return ""
        }

        val friendlyTimeFormatter = SimpleDateFormat(FRIENDLY_TIME_FORMAT, Locale.getDefault())

        return if (dateTime != null)
            friendlyTimeFormatter.format(dateTime)
        else
            ""
    }
}