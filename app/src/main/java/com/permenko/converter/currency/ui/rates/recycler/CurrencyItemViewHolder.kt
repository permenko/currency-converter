package com.permenko.converter.currency.ui.rates.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.permenko.converter.currency.R
import com.permenko.converter.currency.utils.round
import com.permenko.converter.currency.ui.rates.recycler.CurrencyItem.Companion.RATE_ROUND
import kotlinx.android.synthetic.main.item_currency_not_selected.view.tv_amount
import kotlinx.android.synthetic.main.item_currency_not_selected.view.tv_currency
import kotlinx.android.synthetic.main.item_currency_not_selected.view.tv_name
import kotlinx.android.synthetic.main.item_currency_not_selected.view.tv_rate
import kotlinx.android.synthetic.main.item_currency_selected.view.tv_base_amount
import kotlinx.android.synthetic.main.item_currency_selected.view.tv_base_currency
import kotlinx.android.synthetic.main.item_currency_selected.view.tv_base_name

sealed class CurrencyItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

class CurrencyItemSelectedViewHolder(view: View) : CurrencyItemViewHolder(view) {
  fun bind(item: CurrencyItemSelected, onItemClicked: (item: CurrencyItemSelected) -> Unit) {
    itemView.apply {
      tv_base_currency.text = item.currency
      tv_base_amount.text = item.amount.round(RATE_ROUND)
      tv_base_name.text = item.name
      setOnClickListener { onItemClicked(item) }
    }
  }
}

class CurrencyItemNotSelectedViewHolder(view: View) : CurrencyItemViewHolder(view) {
  fun bind(item: CurrencyItemNotSelected, onItemClicked: (item: CurrencyItemNotSelected) -> Unit) {
    itemView.apply {
      tv_currency.text = item.currency
      tv_amount.text = item.amount.round(RATE_ROUND)
      tv_name.text = item.name
      tv_rate.text = resources.getString(R.string.rate, item.currency, item.rate.round(RATE_ROUND))
      setOnClickListener { onItemClicked(item) }
    }
  }
}
