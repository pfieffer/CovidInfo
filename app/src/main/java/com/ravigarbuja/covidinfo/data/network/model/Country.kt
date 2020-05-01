package com.ravigarbuja.covidinfo.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    @SerializedName("Country") val name: String,
    @SerializedName("CountryCode") val countryCode: String,
    @SerializedName("Slug") val slug: String,
    @SerializedName("NewConfirmed") val newConfirmed: Int,
    @SerializedName("TotalConfirmed") val totalConfirmed: Int,
    @SerializedName("NewDeaths") val newDeaths: Int,
    @SerializedName("TotalDeaths") val totalDeaths: Int,
    @SerializedName("NewRecovered") val newRecovered: Int,
    @SerializedName("TotalRecovered") val totalRecovered: Int,
    @SerializedName("Date") val date: String
) : Parcelable