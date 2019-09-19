package com.permenko.converter.currency.ui.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.permenko.converter.currency.utils.SingleLiveEvent
import com.permenko.converter.currency.ui.rates.recycler.CurrencyItem
import com.permenko.converter.currency.ui.rates.recycler.CurrencyItemNotSelected
import com.permenko.converter.currency.ui.rates.recycler.CurrencyItemSelected
import com.permenko.converter.currency.ui.rates.recycler.toCurrencyItems
import javax.inject.Inject

class CurrencyRatesViewModel @Inject constructor(private val repository: CurrencyRatesRepository) : ViewModel() {
  val currencyRates = LiveDataReactiveStreams.fromPublisher(rates())

  private val _toHideLoading = SingleLiveEvent<Unit>()
  val toHideLoading: LiveData<Unit> = _toHideLoading

  private val _scrollToTop = SingleLiveEvent<Unit>()
  val scrollToTop: LiveData<Unit> = _scrollToTop

  private val _toInputOverlay = MutableLiveData<CurrencyItem?>()
  val toInputOverlay: LiveData<CurrencyItem?> = _toInputOverlay

  private fun rates() = repository.rates()
    .doOnNext { _toHideLoading.call() }
    .map { it.toCurrencyItems() }

  fun onCurrencyItemSelectedChanged(amount: Float) {
    repository.changeBaseAmount(amount)
    _toInputOverlay.postValue(null)
  }

  fun onCurrencyItemSelectedClicked(item: CurrencyItemSelected) {
    _scrollToTop.call()
    _toInputOverlay.postValue(item)
  }

  fun onCurrencyItemNotSelectedClicked(item: CurrencyItemNotSelected) {
    _scrollToTop.call()
    _toInputOverlay.postValue(item)
    repository.changeBaseCurrency(item.currency, item.amount)
  }

  fun onOutsideInputClicked() = _toInputOverlay.postValue(null)
}
