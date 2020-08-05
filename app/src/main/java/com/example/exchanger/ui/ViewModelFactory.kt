package com.example.exchanger.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exchanger.repository.AssetsRepository
import com.example.exchanger.repository.RatesRepository

class ViewModelFactory (
    private val ratesRepository: RatesRepository,
    private val assetsRepository: AssetsRepository
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        return ExRatesViewModel(ratesRepository,assetsRepository) as T
    }
}