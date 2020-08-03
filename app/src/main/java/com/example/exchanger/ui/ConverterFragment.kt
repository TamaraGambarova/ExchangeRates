package com.example.exchanger.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.exchanger.R
import kotlinx.android.synthetic.main.fragment_converter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.math.BigDecimal
import java.math.RoundingMode


open class ConverterFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: ViewModelFactory by instance()

    private lateinit var viewModel: ExRatesViewModel

    private var toCurr = "USD"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ExRatesViewModel::class.java)

        currency_drop_down_from.setOnClickListener { showPopUp(it, currency_drop_down_from_label) }
        currency_drop_down_to.setOnClickListener { showPopUp(it, currency_drop_down_to_label) }
        change_currency.setOnClickListener {
            changeCurrency(
                currency_drop_down_from,
                currency_drop_down_to
            )
        }

        bindUI()
    }

    private fun showPopUp(view: View, label: TextView) {
        val popupMenu = PopupMenu(context, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.currency_choice, popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.usd -> {
                    view.setBackgroundResource(R.drawable.usd)
                    label.text = resources.getString(R.string.label_usd)
                }
                R.id.eur -> {
                    view.setBackgroundResource(R.drawable.eur)
                    label.text = resources.getString(R.string.label_eur)
                }
                R.id.rur -> {
                    view.setBackgroundResource(R.drawable.rur)
                    label.text = resources.getString(R.string.label_rur)

                }
                R.id.btc -> {
                    view.setBackgroundResource(R.drawable.btc)
                    label.text = resources.getString(R.string.label_btc)

                }
                R.id.uah -> {
                    view.setBackgroundResource(R.drawable.uah)
                    label.text = resources.getString(R.string.label_uah)

                }
            }
            toCurr = it.title as String
            Log.d("targetCurrency", it.title as String)
            true
        }
    }

    private fun changeCurrency(viewFrom: View, viewTo: View) {
        val temp = viewFrom.background
        viewFrom.background = viewTo.background
        viewTo.background = temp
    }

    private fun toUah(value: BigDecimal, rates: BigDecimal): BigDecimal {
        return value * rates
    }

    private fun fromUah(value: BigDecimal, rates: BigDecimal): BigDecimal {
        return value.divide(rates, RoundingMode.HALF_UP)
    }

    private fun convert(from: String, to: Int, value: BigDecimal, rates: BigDecimal) {
        //if (from == to)
        if (from == resources.getString(R.string.label_uah)) {
            Log.d("checkFrom", "true")
            inputField_to_.text = fromUah(value, rates).toPlainString().toEditable()
        }
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val currentRates = viewModel.rates.await()
        currentRates.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                return@Observer
            }
            Log.d("currentRates", currentRates.value?.size.toString())

            inputField_from_.addTextChangedListener(object : TextWatcher {

                var choice = currentRates.value?.first { value -> value.currency == toCurr }


                override fun afterTextChanged(s: Editable?) {
                    Log.d("Choice", choice?.sale?.toPlainString())
                    Log.d("teXt", currency_drop_down_from_label.text as String)

                    val input_from: BigDecimal? = try {
                        inputField_from_.editableText.toString().toBigDecimal()
                    } catch (e: NumberFormatException) {
                        null
                    }
                    input_from?.let {
                        convert(
                            currency_drop_down_from_label.text as String,
                            currency_drop_down_to.id,
                            input_from,
                            choice!!.sale
                        )
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

        })

    }


    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}