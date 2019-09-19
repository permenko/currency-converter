package com.permenko.converter.currency.ui.rates

import com.jakewharton.rxrelay2.BehaviorRelay
import com.permenko.converter.currency.utils.SchedulersProvider
import com.permenko.converter.currency.network.CurrencyRatesApi
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject

class CurrencyRatesRepositoryReal @Inject constructor(
  private val api: CurrencyRatesApi,
  private val schedulers: SchedulersProvider
) : CurrencyRatesRepository {
  private val cache = BehaviorRelay.create<CurrencyRates>()
  private val currency get() = Flowable.just(cache.value?.baseCurrency ?: INITIAL_CURRENCY)

  override fun rates(): Flowable<CurrencyRates> =
    Flowable.merge(observeRates().doOnNext(cache), cache.toFlowable(LATEST)).distinct()

  private fun observeRates(frequency: Long = UPDATE_FREQUENCY_MS) =
    Flowable.interval(INITIAL_UPDATE_DELAY_MS, frequency, MILLISECONDS)
      .flatMap { currency }
      .switchMap { currency: String ->
        api.rates(currency)
          .subscribeOn(schedulers.io)
          .observeOn(schedulers.main)
          .map { it.toCurrencyRates(cache.value?.baseAmount ?: INITIAL_BASE_AMOUNT) }
          .toFlowable()
          .onErrorResumeNext(Flowable.empty())
      }

  override fun changeBaseAmount(amount: Float) {
    cache.accept(cache.value?.copy(baseAmount = amount) ?: return)
  }

  override fun changeBaseCurrency(currency: String, amount: Float) {
    cache.accept(cache.value?.takeIf { currency != it.baseCurrency }?.baseTo(currency, amount) ?: return)
  }

  private fun CurrencyRates.baseTo(currency: String, amount: Float): CurrencyRates {
    val newBaseRate = rates[currency] ?: throw NoSuchElementException()

    val newRates = mapOf(currency to 1f / newBaseRate) +
      rates.map { (currency, rate) -> currency to rate / newBaseRate }
    return CurrencyRates(amount, newRates, currency, date)
  }

  companion object {
    private const val INITIAL_CURRENCY = "EUR"
    private const val INITIAL_UPDATE_DELAY_MS = 0L
    private const val INITIAL_BASE_AMOUNT = 10F
    private const val UPDATE_FREQUENCY_MS = 3000L
  }
}
