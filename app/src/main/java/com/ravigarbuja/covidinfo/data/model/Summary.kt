package com.ravigarbuja.covidinfo.data.model

import com.google.gson.annotations.SerializedName

data class Summary(
    @SerializedName("Global") val global: Global,
    @SerializedName("Countries") val countries: List<Country>,
    @SerializedName("Date") val date: String
)