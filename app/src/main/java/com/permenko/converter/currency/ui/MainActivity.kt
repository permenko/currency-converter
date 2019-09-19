package com.permenko.converter.currency.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import com.permenko.converter.currency.App
import com.permenko.converter.currency.R
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
  @Inject
  lateinit var fragmentFactory: FragmentFactory

  override fun onCreate(savedInstanceState: Bundle?) {
    App.component().inject(this)
    supportFragmentManager.fragmentFactory = fragmentFactory
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
  }
}
