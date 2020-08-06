package com.example.exchanger.network

import androidx.lifecycle.LiveData

interface NetworkDataSource {
    val downloadedData : LiveData<List<RatesModel>>

    suspend fun fetchCurrentData()
}