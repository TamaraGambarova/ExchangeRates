package com.example.exchanger.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exchanger.repository.Repository

class ViewModelFactory (
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        return ExRatesViewModel(repository) as T
    }
}