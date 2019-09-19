package com.permenko.converter.currency

import android.app.Application
import com.permenko.converter.currency.di.AppComponent
import com.permenko.converter.currency.di.DaggerAppComponent

abstract class App : Application() {

  override fun onCreate() {
    super.onCreate()
    component = appComponent()
  }

  abstract fun appComponent(): AppComponent

  companion object {
    private lateinit var component: AppComponent
    fun component(): AppComponent = component
  }
}

class AppReal : App() {
  override fun appComponent(): AppComponent {
    println("App. Real")
    return DaggerAppComponent.factory().create(this)
  }
}
