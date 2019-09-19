package com.permenko.converter.currency.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.showKeyboard() {
  isFocusableInTouchMode = true
  requestFocus()
  val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun View.hideKeyboard() {
  val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(windowToken, 0)
}
