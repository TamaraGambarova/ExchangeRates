package com.example.exchanger.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.exchanger.network.CurrentRatesResponse
import com.example.exchanger.network.ResponseModel

@Dao
interface CurrentRatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ratesResponse: List<ResponseModel>)

    @Query("select * from current_response")
    fun getRates() : LiveData<List<ResponseModel>>
}
