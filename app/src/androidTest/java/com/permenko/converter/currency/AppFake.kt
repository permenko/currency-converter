package com.permenko.converter.currency

import com.permenko.converter.currency.di.AppComponent
import com.permenko.converter.currency.di.DaggerAppComponentFake

class AppFake : App() {
  override fun appComponent(): AppComponent = DaggerAppComponentFake.factory().create(this)
}
