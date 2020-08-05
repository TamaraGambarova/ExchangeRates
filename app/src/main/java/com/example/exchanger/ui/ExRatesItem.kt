package com.example.exchanger.ui

import android.content.Context
import com.example.exchanger.R
import com.example.exchanger.network.CurrentRatesResponse
import com.example.exchanger.network.ResponseModel
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.ex_rates_item.*

class ExRatesItem(
    private val response: ResponseModel,
    private val context: Context?
) : Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_buy.text = response.price
            //textView_sell.text = response.sale.toPlainString()
            textView_base_rate.text = response.code

            textView_label_buy.text = context?.getString(R.string.label_buy)
            //textView_label_sell.text = context?.getString(R.string.label_sale)


            //IconsConstants.icons[response.currency]?.let { imageView_flag.setImageResource(it) }

        }
    }

    override fun getLayout() = R.layout.response_item
}