package com.example.exchanger.UI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exchanger.repository.RatesRepository

class ViewModelFactory (
    private val ratesRepository: RatesRepository
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        return ExRatesViewModel(ratesRepository) as T
    }
}