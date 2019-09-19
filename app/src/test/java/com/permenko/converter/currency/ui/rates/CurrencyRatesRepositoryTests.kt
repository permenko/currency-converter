package com.permenko.converter.currency.ui.rates

import com.permenko.converter.currency.Currency.Companion.EUR
import com.permenko.converter.currency.Currency.Companion.USD
import com.permenko.converter.currency.utils.SchedulersFake
import com.permenko.converter.currency.utils.awaitCount
import com.permenko.converter.currency.network.CurrencyRatesApiFake
import com.permenko.converter.currency.network.CurrencyRatesApiFake.Companion.NETWORK_INSTANT
import org.junit.Test

class CurrencyRatesRepositoryTests {
  private val fakeApi = CurrencyRatesApiFake(NETWORK_INSTANT)
  private val schedulers = SchedulersFake()

  private fun repository() = CurrencyRatesRepositoryReal(fakeApi, schedulers)

  @Test
  fun rates() {
    val repository = repository()
    val expectedCount = 1
    repository.rates().test().awaitCount(expectedCount + 1, AWAIT_TIMEOUT_MS)
      .assertValueCount(expectedCount)
  }

  @Test
  fun ratesWithInterval() {
    val repository = repository()
    val interval = 3000L
    val expectedCount = 2
    repository.rates().test().awaitCount(expectedCount + 1, interval + AWAIT_TIMEOUT_MS)
      .assertValueCount(expectedCount)
  }

  @Test
  fun changeBaseAmountForEmptyCache() {
    val repository = repository()
    repository.changeBaseAmount(1f)
    val expectedCount = 1
    repository.rates().test().awaitCount(expectedCount + 1, AWAIT_TIMEOUT_MS)
      .assertValueCount(expectedCount)
  }

  @Test
  fun changeBaseAmount() {
    val repository = repository()
    val rate = repository.rates().test().awaitCount(2, AWAIT_TIMEOUT_MS)
      .assertValueCount(1)
    repository.changeBaseAmount(14f)
    rate.awaitCount(3, AWAIT_TIMEOUT_MS)
      .assertValueCount(2)
  }

  @Test
  fun changeBaseCurrencyForEmptyCache() {
    val repository = repository()
    repository.changeBaseCurrency(USD, 1f)
    val expectedCount = 1
    repository.rates().test().awaitCount(expectedCount + 1, AWAIT_TIMEOUT_MS)
      .assertValueCount(expectedCount)
  }

  @Test
  fun changeBaseCurrencyToSameCurrency() {
    val repository = repository()
    val rate = repository.rates().test().awaitCount(2, AWAIT_TIMEOUT_MS).assertValueCount(1)
    repository.changeBaseCurrency(EUR, 15f)
    rate.awaitCount(3, AWAIT_TIMEOUT_MS).assertValueCount(1)
  }

  @Test
  fun changeBaseCurrency() {
    val repository = repository()
    val rate = repository.rates().test().awaitCount(2, AWAIT_TIMEOUT_MS).assertValueCount(1)
    repository.changeBaseCurrency(USD, 14f)
    rate.awaitCount(3, AWAIT_TIMEOUT_MS).assertValueCount(2)
      .assertValueAt(1) { it.baseAmount == 14f && it.baseCurrency == USD }
  }

  @Test
  fun changeBaseCurrencyFromNetwork() {
    val repository = repository()
    val interval = 3000L
    val expectedSize = 3
    val rate = repository.rates().test().awaitCount(2, AWAIT_TIMEOUT_MS).assertValueCount(1)
    repository.changeBaseCurrency(USD, 14f)
    rate.awaitCount(expectedSize + 1, interval + AWAIT_TIMEOUT_MS).assertValueCount(expectedSize)
      .assertValueAt(expectedSize - 1) { it.baseAmount == 14f && it.baseCurrency == USD }
  }

  companion object {
    private const val AWAIT_TIMEOUT_MS = 50L
  }
}
