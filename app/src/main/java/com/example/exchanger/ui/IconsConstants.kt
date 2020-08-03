package com.example.exchanger.ui

import com.example.exchanger.R

const val USD = R.drawable.usd
const val EUR = R.drawable.eur
const val RUR = R.drawable.rur
const val BTC = R.drawable.btc
const val UAH = R.drawable.uah


object IconsConstants{
    val icons = hashMapOf(
        "USD" to USD,
        "EUR" to EUR,
        "RUR" to RUR,
        "BTC" to BTC
    )

    val  convertConstants = hashMapOf(
        "USD" to R.string.label_usd,
        "EUR" to R.string.label_eur,
        "RUR" to R.string.label_rur,
        "BTC" to R.string.label_btc
    )
}