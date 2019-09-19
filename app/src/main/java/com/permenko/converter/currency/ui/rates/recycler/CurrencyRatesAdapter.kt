package com.permenko.converter.currency.ui.rates.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil.calculateDiff
import androidx.recyclerview.widget.RecyclerView
import com.permenko.converter.currency.R

class CurrencyRatesAdapter(
  private val onCurrencyItemSelectedClicked: (currencyItem: CurrencyItemSelected) -> Unit,
  private val onCurrencyItemNotSelectedClicked: (currencyItem: CurrencyItemNotSelected) -> Unit,
  private val items: MutableList<CurrencyItem> = emptyList<CurrencyItem>().toMutableList()
) : RecyclerView.Adapter<CurrencyItemViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder =
    when (viewType) {
      VIEW_TYPE_SELECTED -> CurrencyItemSelectedViewHolder(parent.inflate(R.layout.item_currency_selected))
      VIEW_TYPE_NOT_SELECTED -> CurrencyItemNotSelectedViewHolder(parent.inflate(R.layout.item_currency_not_selected))
      else -> throw IllegalArgumentException("Unsupported view type.")
    }

  private fun ViewGroup.inflate(@LayoutRes layout: Int) = LayoutInflater.from(context).inflate(layout, this, false)

  override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) = when (holder) {
    is CurrencyItemSelectedViewHolder -> holder.bind(
      items[position] as CurrencyItemSelected, onCurrencyItemSelectedClicked
    )
    is CurrencyItemNotSelectedViewHolder -> holder.bind(
      items[position] as CurrencyItemNotSelected, onCurrencyItemNotSelectedClicked
    )
  }

  override fun getItemViewType(position: Int): Int =
    when (items[position]) {
      is CurrencyItemSelected -> VIEW_TYPE_SELECTED
      is CurrencyItemNotSelected -> VIEW_TYPE_NOT_SELECTED
    }

  override fun getItemCount(): Int = items.size

  fun set(items: List<CurrencyItem>) {
    val diffResult = calculateDiff(CurrencyItemsDiff(oldItems = this.items, newItems = items))
    this.items.clear()
    this.items.addAll(items)
    diffResult.dispatchUpdatesTo(this)
  }

  companion object {
    const val VIEW_TYPE_SELECTED = 0
    const val VIEW_TYPE_NOT_SELECTED = 1
  }
}
