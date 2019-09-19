package com.permenko.converter.currency.utils

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener

fun RecyclerView.disableTouches() {
  @Suppress("ClickableViewAccessibility")
  this.setOnTouchListener { _, _ -> true }
  this.addOnItemTouchListener(DisabledItemTouchListener)
}

fun RecyclerView.enableTouches() {
  @Suppress("ClickableViewAccessibility")
  this.setOnTouchListener(null)
  this.removeOnItemTouchListener(DisabledItemTouchListener)
}

private object DisabledItemTouchListener : OnItemTouchListener {
  override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
  }

  override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
    return true
  }

  override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
  }
}
