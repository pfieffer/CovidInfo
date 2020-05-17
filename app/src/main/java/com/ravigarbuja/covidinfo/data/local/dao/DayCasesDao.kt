package com.ravigarbuja.covidinfo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ravigarbuja.covidinfo.data.model.DayCase

@Dao
interface DayCasesDao {
    @Query("SELECT * FROM day_case_table")
    suspend fun getDayCases(): List<DayCase>

    @Query("SELECT * FROM day_case_table WHERE countryCode = :countryCode")
    suspend fun getDayCasesFor(countryCode: String): List<DayCase>

    @Insert
    suspend fun insertDayCases(dayCases: List<DayCase>)

    //clear last inserted DayCase for a country
    @Query("DELETE FROM day_case_table where countryCode = :countryCode")
    suspend fun clearDayCasesDataFor(countryCode: String)

    //clear table
    @Query("DELETE FROM day_case_table")
    suspend fun clear()
}