package com.example.exchanger.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.tokend.sdk.api.TokenDApi
import org.tokend.sdk.api.base.params.PagingOrder
import org.tokend.sdk.api.base.params.PagingParamsV2
import org.tokend.sdk.api.generated.resources.AssetPairResource
import org.tokend.sdk.api.v3.assetpairs.params.AssetPairsPageParams
import org.tokend.sdk.utils.SimplePagedResourceLoader

const val ROOT_URL = "https://api.staging.conto.me/"
val api = TokenDApi(ROOT_URL)

class ExRatesNetworkDataSourceImpl(
    private val apiService: ApiService
) : ExRatesNetworkDataSource {

    private val _downloadedRates = MutableLiveData<List<CurrentRatesResponse>>()
    private val downloadedRates = MutableLiveData<List<ResponseModel>>()

    private val _downloadedAssets = MutableLiveData<List<AssetPairResource>>()
    private val downloadedAssets = MutableLiveData<List<ResponseModel>>()


    override fun downloadedRates(): LiveData<List<ResponseModel>>{
        downloadedRates.value = _downloadedRates.toModelResponse()
        return downloadedRates
    }
    override fun downloadedAssets(): LiveData<List<ResponseModel>>{
        downloadedAssets.value = _downloadedAssets.assetToModelResponse()
        return downloadedAssets
    }

    override suspend fun fetchCurrentRates() {
        try{
            val fetchedRates = apiService.getCurrentRates().await()

            Log.d("test", fetchedRates[0].currency)
            _downloadedRates.postValue(fetchedRates)
        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }
    }
    fun MutableLiveData<List<CurrentRatesResponse>>.toModelResponse() : List<ResponseModel>? {
        return this.value?.map {
            ResponseModel(ApiType.PRIVAT, it.currency, it.buy.toPlainString())
        }
    }

    override fun fetchCurrentAssets() {
        try{
            Log.d("test", "passed")

            val fetchedAssets = SimplePagedResourceLoader({ nextCursor ->
                api.v3.assetPairs.get(
                    AssetPairsPageParams(
                        pagingParams = PagingParamsV2(
                            order = PagingOrder.ASC,
                            page = nextCursor
                        )
                    )
                )
            }).loadAll().execute().get()
            _downloadedAssets.postValue(fetchedAssets)

        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }
    }

    private fun MutableLiveData<List<AssetPairResource>>.assetToModelResponse() : List<ResponseModel>? {
        return this.value?.map {
            ResponseModel(ApiType.TOKEND, it.baseAsset.id, it.price.toPlainString())
        }
    }
}