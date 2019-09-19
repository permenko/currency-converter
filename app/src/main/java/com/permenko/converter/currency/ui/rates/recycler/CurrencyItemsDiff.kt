package com.permenko.converter.currency.ui.rates.recycler

import androidx.recyclerview.widget.DiffUtil

class CurrencyItemsDiff(
  private val oldItems: List<CurrencyItem>,
  private val newItems: List<CurrencyItem>
) : DiffUtil.Callback() {
  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
    oldItems[oldItemPosition].currency == newItems[newItemPosition].currency

  override fun getOldListSize(): Int = oldItems.size

  override fun getNewListSize(): Int = newItems.size

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
    oldItems[oldItemPosition] == newItems[newItemPosition]
}
