package com.example.exchanger.repository

import androidx.lifecycle.LiveData
import com.example.exchanger.db.CurrentRatesDao
import com.example.exchanger.network.CurrentRatesResponse
import com.example.exchanger.network.ExRatesNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RatesRepositoryImpl(
    private val currentRatesDao: CurrentRatesDao,
    private val currentRatesNetworkDataSource: ExRatesNetworkDataSource
) : RatesRepository {
    init {
        currentRatesNetworkDataSource.downloadedRates.observeForever{
            newRates->saveFetchedCurrentRates(newRates)
        }
    }

    override suspend fun getCurrentRates(): LiveData<out List<CurrentRatesResponse>> {
        return withContext(Dispatchers.IO){
            initData()
            return@withContext currentRatesDao.getRates()
        }

    }

    private fun saveFetchedCurrentRates(fetchedRates : List<CurrentRatesResponse>){
        GlobalScope.launch (Dispatchers.IO){
            currentRatesDao.insert(fetchedRates)
        }
    }

    private suspend fun initData(){
        currentRatesNetworkDataSource.fetchCurrentRates()
    }

}