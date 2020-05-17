package com.ravigarbuja.covidinfo.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.data.model.Global
import java.lang.reflect.Type


class Converters {

    /**
     * Convert list of [Country] json to string
     */
    @TypeConverter
    fun countriesListToString(list: List<Country>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Country>>() {}.type
        return gson.toJson(list, type)
    }

    /**
     * Convert json string to list of [Country]
     */
    @TypeConverter
    fun countriesStringToList(json: String): List<Country> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Country>>() {}.type
        return gson.fromJson(json, type)
    }


    /**
     * Convert [Global] to json string
     */
    @TypeConverter
    fun globalToString(global: Global): String {
        val gson = Gson()
        val type: Type = object : TypeToken<Global>() {}.type
        return gson.toJson(global, type)
    }

    /**
     * Convert json string that can represent [Global]
     */
    @TypeConverter
    fun globalStringToObject(json: String): Global {
        val gson = Gson()
        val type: Type = object : TypeToken<Global>() {}.type
        return gson.fromJson(json, type)
    }
}