package com.example.exchanger.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.math.BigDecimal

@Entity(tableName = "current_response", primaryKeys = ["type", "code"])
@TypeConverters(Converters::class)
data class RatesModel (
    val type: String,
    val code: String,
    val price: BigDecimal
)