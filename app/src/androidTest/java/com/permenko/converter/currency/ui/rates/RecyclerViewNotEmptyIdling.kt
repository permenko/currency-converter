package com.permenko.converter.currency.ui.rates

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback

class RecyclerViewNotEmptyIdling(private val recyclerView: RecyclerView) : IdlingResource {
  override fun getName(): String = "RecyclerViewNotEmptyIdling"

  override fun isIdleNow(): Boolean {
    val count = recyclerView.adapter?.itemCount ?: return false
    return count > 0
  }

  override fun registerIdleTransitionCallback(callback: ResourceCallback?) {
    recyclerView.adapter?.registerAdapterDataObserver(object : AdapterDataObserver() {
      override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        val count = recyclerView.adapter?.itemCount ?: return
        if (count > 0) {
          recyclerView.adapter?.unregisterAdapterDataObserver(this)
          callback?.onTransitionToIdle()
        }
      }
    })
  }
}
