package com.ravigarbuja.covidinfo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.ravigarbuja.covidinfo.data.local.Converters

@Entity(tableName = "summary_table")
@TypeConverters(Converters::class)
data class Summary(
    @PrimaryKey(autoGenerate = true) var id: Int, // for room only
    @SerializedName("Global") val global: Global,
    @SerializedName("Countries") val countries: List<Country>,
    @SerializedName("Date") val date: String
)