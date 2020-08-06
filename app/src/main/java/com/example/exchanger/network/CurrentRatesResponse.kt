package com.example.exchanger.network

import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


@Entity(tableName = "current_rates", primaryKeys = ["base_currency", "currency"])
@TypeConverters(Converters::class)
data class CurrentRatesResponse (
    @SerializedName("base_ccy") val base_currency : String,
    @SerializedName("ccy") val currency : String,
    @SerializedName("buy") val buy : BigDecimal,
    @SerializedName("sale") val sale : BigDecimal
)

class Converters {
    @TypeConverter
    fun bigDecimalToString(value: BigDecimal?): String? {
        return value?.toPlainString()
    }

    @TypeConverter
    fun stringToBigDecimal(value: String?): BigDecimal? {
        return value?.toBigDecimal()
    }
}