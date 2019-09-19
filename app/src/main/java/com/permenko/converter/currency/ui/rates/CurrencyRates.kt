package com.permenko.converter.currency.ui.rates

import com.permenko.converter.currency.network.CurrencyRatesResponse

data class CurrencyRates(
  val baseAmount: Float,
  val rates: Map<String, Float>,
  val baseCurrency: String,
  val date: String
)

fun CurrencyRatesResponse.toCurrencyRates(baseAmount: Float): CurrencyRates =
  CurrencyRates(baseAmount, this.rates, this.baseCurrency, this.date)
