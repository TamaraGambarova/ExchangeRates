package com.example.exchanger.network

import androidx.lifecycle.LiveData
import com.example.exchanger.db.CurrentRatesDao
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

    override suspend fun getCurrentRates(): LiveData<List<CurrentRatesResponse>> {
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