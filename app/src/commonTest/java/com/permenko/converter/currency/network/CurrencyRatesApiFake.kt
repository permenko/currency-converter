package com.permenko.converter.currency.network

import com.permenko.converter.currency.Currency.Companion.EUR
import com.permenko.converter.currency.Currency.Companion.RUB
import com.permenko.converter.currency.Currency.Companion.USD
import io.reactivex.Single
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject
import kotlin.random.Random

class CurrencyRatesApiFake @Inject constructor(private val networkDelay: Long) : CurrencyRatesApi {
  private val eurResponse = CurrencyRatesResponse(mapOf(RUB to 80f, USD to 1.25f), EUR, "09-15-2019")
  private val usdResponse = CurrencyRatesResponse(mapOf(EUR to 0.8f, RUB to 64f), USD, "09-15-2019")
  private val rubResponse = CurrencyRatesResponse(mapOf(EUR to 0.0125f, USD to 0.015625f), RUB, "09-15-2019")
  private val random get() = 1 + Random.nextInt(-10, 10) / 100F

  override fun rates(base: String?): Single<CurrencyRatesResponse> = when (base) {
    null, EUR -> Single.timer(networkDelay, MILLISECONDS).flatMap { Single.just(eurResponse.randomize()) }
    USD -> Single.just(usdResponse.randomize())
    RUB -> Single.just(rubResponse.randomize())
    else -> throw IllegalArgumentException()
  }

  private fun CurrencyRatesResponse.randomize() =
    copy(rates = rates.map { (currency, rate) -> currency to rate * random }.toMap())

  companion object {
    const val NETWORK_DELAY_MS = 1000L
    const val NETWORK_INSTANT = 0L
  }
}
