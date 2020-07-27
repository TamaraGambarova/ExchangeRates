package com.example.exchanger.UI

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.exchanger.repository.RatesRepository
import com.example.exchanger.util.lazyDeffered

class ExRatesViewModel(
        private val ratesRepository: RatesRepository
) : ViewModel(){
    val rates by lazyDeffered {
        ratesRepository.getCurrentRates()
    }
}