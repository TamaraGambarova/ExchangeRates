package com.example.exchanger.network

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.connection.ConnectInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val API_KEY = "b1f445aa71acd3dcaf3f2d4172b44258"
//https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5
interface ApiService{

    @GET("pubinfo?json&exchange&coursid=5")
    fun getCurrentRates(): Deferred<List<CurrentRatesResponse>>

    companion object{
        operator fun invoke(
            connectInterceptor: ConnectivityInterceptor
        ): ApiService {
            val requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectInterceptor)
                .build()

            val r =  Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.privatbank.ua/p24api/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

            return r
        }
    }
}