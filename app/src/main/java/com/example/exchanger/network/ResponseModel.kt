package com.example.exchanger.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_response", primaryKeys = ["type", "code"])

data class ResponseModel (
    val type: String,
    val code: String,
    val price: String
)