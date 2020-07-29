package com.example.exchanger.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.exchanger.network.CurrentRatesResponse

@Dao
interface CurrentRatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ratesResponse: List<CurrentRatesResponse>)

    @Query("select * from current_rates")
    fun getRates() : LiveData<List<CurrentRatesResponse>>
}
