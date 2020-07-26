package com.example.exchanger

import android.app.Application
import com.example.exchanger.UI.ViewModelFactory
import com.example.exchanger.db.RatesDatabase
import com.example.exchanger.network.*
import com.example.exchanger.repository.RatesRepository
import com.example.exchanger.repository.RatesRepositoryImpl
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ExchangerApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy{
        import(androidXModule(this@ExchangerApp))
        //db
        bind() from singleton { RatesDatabase(instance()) }
        bind() from singleton { instance<RatesDatabase>().currentRates()}

        //network
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiService(instance()) }
        bind<ExRatesNetworkDataSource>() with singleton { ExRatesNetworkDataSourceImpl(instance()) }

        //repository
        bind<RatesRepository>() with singleton {
            RatesRepositoryImpl(
                instance(),
                instance()
            )
        }
        bind() from provider{ ViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

    }
}
