package com.example.exchanger.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exchanger.R
import com.example.exchanger.network.CurrentRatesResponse
import com.example.exchanger.network.ResponseModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
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
        val all = MutableLiveData<List<ResponseModel>>()
        val currentRates = viewModel.rates.await()
        val curentAssets = viewModel.assets.await()

        var list: MutableList<ResponseModel> = ArrayList()
        list.addAll(currentRates.value!!)
        list.addAll(curentAssets.value!!)
        all.value = list
       // all.value = currentRates.value?.plus(curentAssets.value)
        all.observe(viewLifecycleOwner, Observer {
            if(it == null){
                return@Observer
            }
            group_loading.visibility = View.GONE

            initRecyclerView(it.toExRatesItems())
        })
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

    private fun List<ResponseModel>.toExRatesItems() : List<ExRatesItem>{
        return this.map{
            ExRatesItem(it, context)
        }
    }
}