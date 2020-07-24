package com.example.exchanger.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exchanger.network.ApiService
import com.example.exchanger.network.CurrentRatesResponse
import com.example.exchanger.network.ExRatesNetworkDataSource

class ExRatesNetworkDataSourceImpl(
    private val apiService: ApiService
) : ExRatesNetworkDataSource {

    private val _downloadedRates = MutableLiveData<List<CurrentRatesResponse>>()

    override val downloadedRates: LiveData<List<CurrentRatesResponse>>
        get() = _downloadedRates

    override suspend fun fetchCurrentRates() {
        try{
            val fetchedRates = apiService.getCurrentRates().await()
            _downloadedRates.postValue(fetchedRates)
        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }
    }
}