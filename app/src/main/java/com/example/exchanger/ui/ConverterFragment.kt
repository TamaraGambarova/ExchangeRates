package com.example.exchanger.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.exchanger.R
import kotlinx.android.synthetic.main.fragment_converter.*


open class ConverterFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_converter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        currency_drop_down_from.setOnClickListener { showPopUp(it) }
        currency_drop_down_to.setOnClickListener { showPopUp(it) }
        change_currency.setOnClickListener{changeCurrency(currency_drop_down_from, currency_drop_down_to)}
    }

    private fun showPopUp(view: View) {
        val popupMenu = PopupMenu(context, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.currency_choice, popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.usd -> {
                    view.setBackgroundResource(R.drawable.usd)
                }
                R.id.eur -> {
                    view.setBackgroundResource(R.drawable.eur)
                }
                R.id.rur -> {
                    view.setBackgroundResource(R.drawable.rur)
                }
                R.id.btc -> {
                    view.setBackgroundResource(R.drawable.btc)
                }
            }
            true
        }
    }

    private fun changeCurrency(viewFrom: View, viewTo: View){
        val temp = viewFrom.background
        viewFrom.background = viewTo.background
        viewTo.background = temp
    }


}