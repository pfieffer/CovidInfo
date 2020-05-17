package com.ravigarbuja.covidinfo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ravigarbuja.covidinfo.data.model.Summary

@Dao
interface SummaryDao {
    @Query("SELECT * FROM summary_table")
    suspend fun getSummary(): Summary

    @Insert
    suspend fun insertSummary(summary: Summary)

    @Delete
    suspend fun delete(summary: Summary)

    //clear table
    @Query("DELETE FROM summary_table")
    suspend fun clear()
}