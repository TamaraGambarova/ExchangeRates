package com.example.exchanger.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.exchanger.network.RatesModel

@Dao
interface CurrentRatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rates: List<RatesModel>)

    @Query("select * from current_response")
    fun getRates() : LiveData<List<RatesModel>>
}
