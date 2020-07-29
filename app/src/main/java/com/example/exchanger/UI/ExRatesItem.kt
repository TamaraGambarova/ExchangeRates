package com.example.exchanger.UI

import com.example.exchanger.R
import com.example.exchanger.network.CurrentRatesResponse
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.ex_rates_item.*

class ExRatesItem(
    val response: CurrentRatesResponse
) : Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_buy.text = response.buy.toPlainString()
            textView_sell.text = response.sale.toPlainString()
            textView_base_rate.text = response.currency

            textView_label_buy.text = "Buy"
            textView_label_sell.text = "Sell"


            IconsConstants.icons[response.currency]?.let { imageView_flag.setImageResource(it) }

        }
    }

    override fun getLayout() = R.layout.ex_rates_item
}