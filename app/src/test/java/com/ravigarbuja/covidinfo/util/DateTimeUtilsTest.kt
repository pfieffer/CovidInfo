package com.ravigarbuja.covidinfo.util

import org.junit.Test

import org.junit.Assert.*
import java.util.*

class DateTimeUtilsTest {

    val SUT = DateTimeUtils

    @Test
    fun `test function getReadableDate`() {
        assertEquals("Fri May 01 2020",
            SUT.getReadableDate("2020-05-01T17:37:00Z"))

        assertEquals("Sat May 02 2020",
            SUT.getReadableDate("2020-05-02T17:37:00Z"))

        assertEquals("",
            SUT.getReadableDate("2020:05:02T17:37:00Z"))
    }
}