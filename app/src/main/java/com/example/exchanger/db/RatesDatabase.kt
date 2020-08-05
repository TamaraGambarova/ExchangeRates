package com.example.exchanger.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.exchanger.network.CurrentRatesResponse
import com.example.exchanger.network.ResponseModel

@Database(
    entities = [ResponseModel::class],
    version = 1
)
abstract class RatesDatabase : RoomDatabase() {
    abstract fun currentRates() : CurrentRatesDao

    companion object{
        @Volatile
        private var instance: RatesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context)
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RatesDatabase::class.java, "rates.db"
            )
                .build()
    }
}