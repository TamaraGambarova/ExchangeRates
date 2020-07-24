package com.example.exchanger.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exchanger.network.CURRENT_RATES_ID
import com.example.exchanger.network.CurrentRatesResponse

@Dao
interface CurrentRatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ratesResponse: List<CurrentRatesResponse>)

    @Query("select * from current_rates where id = $CURRENT_RATES_ID")
    fun getRates() : LiveData<List<CurrentRatesResponse>>

}