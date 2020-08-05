package com.example.exchanger.ui

import androidx.lifecycle.ViewModel
import com.example.exchanger.repository.AssetsRepository
import com.example.exchanger.repository.RatesRepository
import com.example.exchanger.util.lazyDeffered

class ExRatesViewModel(
        private val ratesRepository: RatesRepository,
        private val assetsRepository: AssetsRepository
) : ViewModel(){
    val rates by lazyDeffered {
        ratesRepository.getResponseModel()
    }
    val assets by lazyDeffered {
        assetsRepository.getResponseModel()
    }

}