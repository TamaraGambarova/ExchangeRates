package com.example.exchanger.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RatesDataSource(
    private val apiService: ApiService
) : NetworkDataSource {

    private val downloadedRates = MutableLiveData<List<RatesModel>>()


    override val downloadedData: LiveData<List<RatesModel>>
        get() = downloadedRates

    override suspend fun fetchCurrentData() {
        try{
            val fetchedRates = apiService.getCurrentRates().await()
            downloadedRates.postValue(fetchedRates.toRatesModel())
        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }
    }

    private fun List<CurrentRatesResponse>.toRatesModel() : List<RatesModel>? {
        return this.map {
            RatesModel(ApiType.PRIVAT, it.currency, it.buy)
        }
    }
}