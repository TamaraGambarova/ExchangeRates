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

class AssetsDataSource : NetworkDataSource {
    private val downloadedAssets = MutableLiveData<List<RatesModel>>()

    override val downloadedData: LiveData<List<RatesModel>>
        get() = downloadedAssets

    override suspend fun fetchCurrentData() {
        try{
            val fetchedAssets = SimplePagedResourceLoader({ nextCursor ->
                api.v3.assetPairs.get(
                    AssetPairsPageParams(
                        pagingParams = PagingParamsV2(
                            order = PagingOrder.ASC,
                            page = nextCursor
                        ), quoteAsset = "UAH"
                    )
                )
            }).loadAll().execute().get()
            downloadedAssets.postValue(fetchedAssets.assetToModelResponse())

        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }
    }

    private fun List<AssetPairResource>.assetToModelResponse() : List<RatesModel>? {
        return this.map {
            RatesModel(ApiType.TOKEND, it.baseAsset.id, it.price)
        }
    }


}