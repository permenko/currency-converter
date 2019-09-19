package com.permenko.converter.currency.ui.rates

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.permenko.converter.currency.R
import com.permenko.converter.currency.di.VmFactory
import com.permenko.converter.currency.utils.disableTouches
import com.permenko.converter.currency.utils.enableTouches
import com.permenko.converter.currency.utils.hideKeyboard
import com.permenko.converter.currency.utils.round
import com.permenko.converter.currency.utils.showKeyboard
import com.permenko.converter.currency.ui.rates.recycler.CurrencyItem
import com.permenko.converter.currency.ui.rates.recycler.CurrencyItem.Companion.RATE_ROUND
import com.permenko.converter.currency.ui.rates.recycler.CurrencyRatesAdapter
import com.permenko.converter.currency.di.vm
import kotlinx.android.synthetic.main.currency_rates_fragment.et_overlay_amount
import kotlinx.android.synthetic.main.currency_rates_fragment.fl_overlay
import kotlinx.android.synthetic.main.currency_rates_fragment.loadingView
import kotlinx.android.synthetic.main.currency_rates_fragment.rv_currency_rates
import kotlinx.android.synthetic.main.currency_rates_fragment.tv_overlay_currency
import kotlinx.android.synthetic.main.currency_rates_fragment.tv_overlay_name
import javax.inject.Inject

class CurrencyRatesFragment @Inject constructor(
  factory: VmFactory<CurrencyRatesViewModel>
) : Fragment(R.layout.currency_rates_fragment) {
  private val viewModel by vm(factory)
  private val adapter = CurrencyRatesAdapter(
    { item ->
      viewModel.onCurrencyItemSelectedClicked(item)
      rv_currency_rates.disableTouches()
    },
    { item ->
      viewModel.onCurrencyItemNotSelectedClicked(item)
      rv_currency_rates.disableTouches()
    }
  )

  private fun showInput(item: CurrencyItem) {
    fl_overlay.visibility = View.VISIBLE
    tv_overlay_currency.text = item.currency
    tv_overlay_name.text = item.name
    if (et_overlay_amount.text.isEmpty()) et_overlay_amount.setText(item.amount.round(RATE_ROUND))

    et_overlay_amount.setOnEditorActionListener { _, actionId, _ ->
      if (actionId == IME_ACTION_DONE) applyInput(item)
      false
    }
    et_overlay_amount.showKeyboard()
  }

  private fun applyInput(item: CurrencyItem) {
    val amount = et_overlay_amount.text.toString().toFloatOrNull()
    amount?.let { amount_ ->
      hideInput()
      viewModel.onCurrencyItemSelectedChanged(amount_)
    } ?: et_overlay_amount.setText(item.amount.round(RATE_ROUND))
  }

  private fun hideInput() {
    fl_overlay.visibility = GONE
    rv_currency_rates.enableTouches()
    et_overlay_amount.text = null
    et_overlay_amount.hideKeyboard()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    rv_currency_rates.layoutManager = LinearLayoutManager(context)
    rv_currency_rates.adapter = adapter
    viewModel.toHideLoading.observe(viewLifecycleOwner, Observer { loadingView.visibility = GONE })
    viewModel.currencyRates.observe(viewLifecycleOwner, Observer {
      val recyclerViewState = rv_currency_rates.layoutManager?.onSaveInstanceState()
      adapter.set(it)
      // Prevent unnecessary scroll.
      rv_currency_rates.layoutManager?.onRestoreInstanceState(recyclerViewState)
    })
    rv_currency_rates.itemAnimator =  object : DefaultItemAnimator() {
      override fun onMoveFinished(item: ViewHolder?) {
        println("Animation; onMoveFinished")
        super.onMoveFinished(item)
      }

      override fun setMoveDuration(moveDuration: Long) {
        println("Animation; moveDuration=$moveDuration")
        super.setMoveDuration(moveDuration)
      }

      override fun onAnimationFinished(viewHolder: RecyclerView.ViewHolder) {
        println("Animation; onAnimationFinished")
      }
    }
    // adapter.registerAdapterDataObserver()
    viewModel.scrollToTop.observe(viewLifecycleOwner, Observer { rv_currency_rates.smoothScrollToPosition(0) })
    viewModel.toInputOverlay.observe(viewLifecycleOwner, Observer {
      if (it == null) {
        hideInput()
      } else {
        showInput(it)
      }
    })
    fl_overlay.setOnClickListener { viewModel.onOutsideInputClicked() }
  }
}
