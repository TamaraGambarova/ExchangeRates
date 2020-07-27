package com.example.exchanger.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "current_rates")
data class CurrentRatesResponse (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @SerializedName("ccy") val currency : String,
    @SerializedName("buy") val buy : String,
    @SerializedName("sale") val sale : String
)