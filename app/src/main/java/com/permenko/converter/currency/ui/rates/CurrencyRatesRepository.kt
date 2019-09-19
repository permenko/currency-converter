package com.permenko.converter.currency.ui.rates

import io.reactivex.Flowable

interface CurrencyRatesRepository {
  fun rates(): Flowable<CurrencyRates>
  fun changeBaseAmount(amount: Float)
  fun changeBaseCurrency(currency: String, amount: Float)
}
