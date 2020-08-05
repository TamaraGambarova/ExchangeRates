package com.example.exchanger.network

import androidx.lifecycle.LiveData
import com.example.exchanger.network.CurrentRatesResponse

interface ExRatesNetworkDataSource {
    //val downloadedRates : LiveData<List<ResponseModel>>

    fun  downloadedRates():LiveData<List<ResponseModel>>
    fun downloadedAssets():LiveData<List<ResponseModel>>
    suspend fun fetchCurrentRates()
     fun fetchCurrentAssets()
}