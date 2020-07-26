package com.example.exchanger.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exchanger.R
import com.example.exchanger.network.CurrentRatesResponse
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.ex_rates_item.*
import kotlinx.android.synthetic.main.fragment_ex_rates.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

open class ExRatesFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ViewModelFactory by instance()

    private lateinit var viewModel: ExRatesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ex_rates, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(ExRatesViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val currentRates = viewModel.rates.await()

        currentRates.observe(viewLifecycleOwner, Observer {
            if(it == null){
                return@Observer
            }
            update(it)
            initRecyclerView(it.toExRatesItems())
        })
    }

    private fun update(rates: List<CurrentRatesResponse>){
        for(item in rates){
            textView_buy.text = item.buy
            textView_sell.text = item.sale
        }
    }

    private fun initRecyclerView(items:  List<ExRatesItem>){
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ExRatesFragment.context)
            adapter = groupAdapter
        }


    }
    private fun List<CurrentRatesResponse>.toExRatesItems() : List<ExRatesItem>{
        return this.map{
            ExRatesItem(it)
        }
    }
}