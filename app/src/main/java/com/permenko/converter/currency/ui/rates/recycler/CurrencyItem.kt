package com.permenko.converter.currency.ui.rates.recycler

import com.permenko.converter.currency.utils.CurrencyNames
import com.permenko.converter.currency.ui.rates.CurrencyRates

sealed class CurrencyItem {
  abstract val currency: String
  abstract val amount: Float
  abstract val name: String

  companion object {
    const val RATE_ROUND = 2
  }
}

data class CurrencyItemNotSelected(
  override val currency: String,
  override val amount: Float,
  override val name: String,
  val rate: Float
) : CurrencyItem()

data class CurrencyItemSelected(
  override val currency: String,
  override val amount: Float,
  override val name: String
) : CurrencyItem()

fun CurrencyRates.toCurrencyItems(): List<CurrencyItem> {
  val itemSelected = CurrencyItemSelected(baseCurrency, baseAmount, CurrencyNames.name(baseCurrency))
  val itemsNotSelected = rates
    .mapNotNull { (currency, rate) ->
      if (currency == baseCurrency) return@mapNotNull null
      return@mapNotNull CurrencyItemNotSelected(currency, (this.baseAmount * rate), CurrencyNames.name(currency), rate)
    }
    .sortedBy { it.currency }
  return listOf(itemSelected) + itemsNotSelected
}
