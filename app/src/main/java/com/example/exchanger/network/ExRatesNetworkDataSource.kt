package com.example.exchanger.network

import androidx.lifecycle.LiveData
import com.example.exchanger.network.CurrentRatesResponse

interface ExRatesNetworkDataSource {
    val downloadedRates : LiveData<CurrentRatesResponse>

    suspend fun fetchCurrentRates()
}