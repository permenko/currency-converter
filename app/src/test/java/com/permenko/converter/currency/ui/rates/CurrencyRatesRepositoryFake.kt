package com.permenko.converter.currency.ui.rates

import com.jakewharton.rxrelay2.BehaviorRelay
import com.permenko.converter.currency.Currency.Companion.EUR
import com.permenko.converter.currency.Currency.Companion.RUB
import com.permenko.converter.currency.Currency.Companion.USD
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.Flowable

class CurrencyRatesRepositoryFake : CurrencyRatesRepository {
  private val eurRates = CurrencyRates(10f, mapOf(RUB to 80f, USD to 1.25f), EUR, "09-15-2019")
  private val usdRates = CurrencyRates(10f, mapOf(EUR to 0.8f, RUB to 64f), USD, "09-15-2019")
  private val rubRates = CurrencyRates(10f, mapOf(EUR to 0.0125f, USD to 0.015625f), RUB, "09-15-2019")
  private val cache = BehaviorRelay.createDefault(eurRates)

  override fun rates(): Flowable<CurrencyRates> = cache.toFlowable(LATEST)

  override fun changeBaseAmount(amount: Float) {
    cache.accept(cache.value?.copy(baseAmount = amount) ?: return)
  }

  override fun changeBaseCurrency(currency: String, amount: Float) {
    val rates = when (currency) {
      EUR -> eurRates
      USD -> usdRates
      RUB -> rubRates
      else -> throw IllegalArgumentException()
    }
    cache.accept(rates.copy(baseAmount = amount))
  }
}
