package com.example.exchanger.repository

import androidx.lifecycle.LiveData
import com.example.exchanger.network.CurrentRatesResponse
import com.example.exchanger.network.ResponseModel

interface RatesRepository{
    suspend fun getResponseModel() : LiveData<out List<ResponseModel>>
    //suspend fun getCurrentRates() : LiveData<out List<CurrentRatesResponse>>
    //suspend fun deleteOldRates()
}