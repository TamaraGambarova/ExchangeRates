package com.example.exchanger.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0
@Entity(tableName = "current_rates")
data class CurrentRatesResponse (
    @SerializedName("ccy") val currency : String,
    @SerializedName("buy") val buy : String,
    @SerializedName("sale") val sale : String
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}