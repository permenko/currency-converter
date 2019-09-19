package com.permenko.converter.currency.ui.rates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.permenko.converter.currency.utils.assertOneValue
import com.permenko.converter.currency.ui.rates.recycler.CurrencyItemNotSelected
import com.permenko.converter.currency.ui.rates.recycler.CurrencyItemSelected
import org.junit.Rule
import org.junit.Test

class CurrencyRatesViewModelTests {
  private val repository = CurrencyRatesRepositoryFake()
  private fun viewModel() = CurrencyRatesViewModel(repository)

  @get:Rule val rule = InstantTaskExecutorRule()

  @Test
  fun init() {
    val viewModel = viewModel()
    viewModel.currencyRates.test().assertOneValue()
    viewModel.toHideLoading.test().assertOneValue()
  }

  @Test
  fun selectedItemClicked() {
    val viewModel = viewModel()
    val latestItems = viewModel.currencyRates.value ?: return
    val selectedItem = latestItems.mapNotNull { if (it is CurrencyItemSelected) it else null }.first()
    viewModel.onCurrencyItemSelectedClicked(selectedItem)
    viewModel.scrollToTop.test().assertOneValue()
    viewModel.toInputOverlay.test().assertOneValue()
    viewModel.currencyRates.test().assertOneValue(latestItems)
  }

  @Test
  fun selectedItemChanged() {
    val viewModel = viewModel()
    val expectedAmount = 100f
    viewModel.onCurrencyItemSelectedChanged(expectedAmount)
    viewModel.currencyRates.test().assertValue { it.first().amount == expectedAmount }
    viewModel.toInputOverlay.test().assertOneValue(null)
  }

  @Test
  fun notSelectedItemClicked() {
    val viewModel = viewModel()
    val latestItems = viewModel.currencyRates.value ?: return
    val firstNotSelectedItem = latestItems.mapNotNull { if (it is CurrencyItemNotSelected) it else null }.first()
    viewModel.onCurrencyItemNotSelectedClicked(firstNotSelectedItem)
    viewModel.scrollToTop.test().assertOneValue()
    viewModel.toInputOverlay.test().assertOneValue()
    viewModel.currencyRates.test().assertValue { it.first().currency == firstNotSelectedItem.currency }
  }

  @Test
  fun outsideInputClicked() {
    val viewModel = viewModel()
    viewModel.onOutsideInputClicked()
    viewModel.toInputOverlay.test().assertOneValue(null)
  }
}
