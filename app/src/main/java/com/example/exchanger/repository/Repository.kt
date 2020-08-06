package com.example.exchanger.repository

import androidx.lifecycle.LiveData
import com.example.exchanger.db.CurrentRatesDao
import com.example.exchanger.network.NetworkDataSource
import com.example.exchanger.network.RatesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(
    private val currentRatesDao: CurrentRatesDao,
    private val currentRatesDataSource: NetworkDataSource,
    private val currentAssetsDataSource: NetworkDataSource
) {

    init {
        currentRatesDataSource.downloadedData.observeForever{
                newRates->saveFetchedCurrentData(newRates)
        }
        currentAssetsDataSource.downloadedData.observeForever {
                newAssets->saveFetchedCurrentData(newAssets)
        }
    }
    suspend fun getCurrentData(): LiveData<out List<RatesModel>> {
        return withContext(Dispatchers.IO){
            initData()

            return@withContext currentRatesDao.getRates()
        }
    }

    private fun saveFetchedCurrentData(data : List<RatesModel>){
        GlobalScope.launch (Dispatchers.IO){
            currentRatesDao.insert(data)
        }
    }

    private suspend fun initData(){
        currentRatesDataSource.fetchCurrentData()
        currentAssetsDataSource.fetchCurrentData()
    }
}