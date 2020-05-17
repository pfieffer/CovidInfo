package com.ravigarbuja.covidinfo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ravigarbuja.covidinfo.data.local.dao.DayCasesDao
import com.ravigarbuja.covidinfo.data.local.dao.SummaryDao
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.data.model.Summary

@Database(entities = [Summary::class, DayCase::class], version = 1)
abstract class CovidInfoDB : RoomDatabase() {
    abstract fun summaryDao(): SummaryDao
    abstract fun dayCasesDao(): DayCasesDao
}