package com.example.exchanger

import android.app.Application
import com.example.exchanger.ui.ViewModelFactory
import com.example.exchanger.db.RatesDatabase
import com.example.exchanger.network.*
import com.example.exchanger.repository.Repository
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
        bind<NetworkDataSource>(tag = "rates") with singleton { RatesDataSource(instance()) }
        bind<NetworkDataSource>(tag = "assets") with singleton { AssetsDataSource() }
        //bind<ExRatesNetworkDataSource>() with singleton { AssetesDataSourceImpl() }

        //repository
        bind() from singleton {
            Repository(
                instance(),
                instance(tag = "rates"),
                instance(tag = "assets")
            )
        }

        bind() from provider{ ViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

    }
}
