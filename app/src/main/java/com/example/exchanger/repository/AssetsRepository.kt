package com.example.exchanger.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exchanger.db.CurrentRatesDao
import com.example.exchanger.network.CurrentRatesResponse
import com.example.exchanger.network.ExRatesNetworkDataSource
import com.example.exchanger.network.ResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*const val ROOT_URL = "https://api.staging.conto.me/"
val api = TokenDApi(ROOT_URL)*/

class AssetsRepository(
    private val currentRatesDao: CurrentRatesDao,
    private val currentAssetsNetworkDataSource: ExRatesNetworkDataSource
) : RatesRepository {
    init{
        currentAssetsNetworkDataSource.downloadedAssets().observeForever {
                newRates->saveFetchedCurrentRates(newRates)
        }
    }
    override suspend fun getResponseModel(): LiveData<out List<ResponseModel>> {
        return withContext(Dispatchers.IO){
            currentAssetsNetworkDataSource.fetchCurrentAssets()
            return@withContext currentRatesDao.getRates()
        }
    }

    private fun saveFetchedCurrentRates(fetchedRates: List<ResponseModel>) {
        GlobalScope.launch(Dispatchers.IO) {
            currentRatesDao.insert(fetchedRates)
        }
    }

}