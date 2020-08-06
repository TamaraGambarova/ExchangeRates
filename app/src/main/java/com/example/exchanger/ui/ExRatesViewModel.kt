package com.example.exchanger.ui

import androidx.lifecycle.ViewModel
import com.example.exchanger.repository.Repository
import com.example.exchanger.util.lazyDeffered

class ExRatesViewModel(
    repository: Repository
) : ViewModel(){
    val data by lazyDeffered {
        repository.getCurrentData()
    }

}