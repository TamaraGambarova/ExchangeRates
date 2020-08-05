package com.example.exchanger.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.exchanger.db.CurrentRatesDao
import com.example.exchanger.network.CurrentRatesResponse
import com.example.exchanger.network.ExRatesNetworkDataSource
import com.example.exchanger.network.ResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RatesRepositoryImpl(
    private val currentRatesDao: CurrentRatesDao,
    private val currentRatesNetworkDataSource: ExRatesNetworkDataSource
) : RatesRepository {
    init {
        currentRatesNetworkDataSource.downloadedRates().observeForever{
            newRates->saveFetchedCurrentRates(newRates)
        }
    }
    override suspend fun getResponseModel(): LiveData<out List<ResponseModel>> {
        return withContext(Dispatchers.IO){
            initData()

            return@withContext currentRatesDao.getRates()
        }
    }

    private fun saveFetchedCurrentRates(fetchedRates : List<ResponseModel>){
        GlobalScope.launch (Dispatchers.IO){
            currentRatesDao.insert(fetchedRates)
        }
    }

    private suspend fun initData(){
        currentRatesNetworkDataSource.fetchCurrentRates()
    }



}