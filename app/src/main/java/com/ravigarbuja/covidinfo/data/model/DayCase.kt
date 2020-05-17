package com.ravigarbuja.covidinfo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "day_case_table")
data class DayCase(
    @PrimaryKey(autoGenerate = true) var id: Int, // for room only
    @SerializedName("Country") val country: String,
    @SerializedName("CountryCode") val countryCode: String,
    @SerializedName("Province") val province: String,
    @SerializedName("City") val city: String,
    @SerializedName("CityCode") val cityCode: String,
    @SerializedName("Lat") val lat: Int,
    @SerializedName("Lon") val lon: Int,
    @SerializedName("Confirmed") val confirmed: Int,
    @SerializedName("Deaths") val deaths: Int,
    @SerializedName("Recovered") val recovered: Int,
    @SerializedName("Active") val active: Int,
    @SerializedName("Date") val date: String
)