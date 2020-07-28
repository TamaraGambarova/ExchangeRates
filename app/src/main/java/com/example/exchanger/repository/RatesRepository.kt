package com.example.exchanger.repository

import androidx.lifecycle.LiveData
import com.example.exchanger.network.CurrentRatesResponse

interface RatesRepository{
    suspend fun getCurrentRates() : LiveData<out List<CurrentRatesResponse>>
    suspend fun deleteOldRates()
}