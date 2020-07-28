package com.example.exchanger.repository

import android.util.Log
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
            Log.d("REPOs", currentRatesDao.getRates().value?.size.toString())

            return@withContext currentRatesDao.getRates()
        }

    }

    override suspend fun deleteOldRates() {
        currentRatesDao.deleteOldRates()
    }

    private fun saveFetchedCurrentRates(fetchedRates : List<CurrentRatesResponse>){
        GlobalScope.launch (Dispatchers.IO){
            currentRatesDao.insert(fetchedRates)
        }
    }

    private suspend fun initData(){
        deleteOldRates()
        currentRatesNetworkDataSource.fetchCurrentRates()
    }

}