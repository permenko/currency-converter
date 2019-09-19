package com.permenko.converter.currency

import com.permenko.converter.currency.di.AppComponent
import com.permenko.converter.currency.di.DaggerAppComponentFake

class AppFake : App() {
  override fun appComponent(): AppComponent {
    println("App. Fake")
    return DaggerAppComponentFake.factory().create(this)
  }
}
