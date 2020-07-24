package com.example.exchanger.network

import androidx.lifecycle.LiveData

interface RatesRepository{
    suspend fun getCurrentRates() : LiveData<List<CurrentRatesResponse>>

}