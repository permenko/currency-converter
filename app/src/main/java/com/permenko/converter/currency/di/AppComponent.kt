package com.permenko.converter.currency.di

import android.content.Context
import com.permenko.converter.currency.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
  modules = [
    NetworkModule::class,
    SchedulersModule::class,
    FragmentBindings::class,
    RatesBindings::class
  ]
)
@Singleton
open interface AppComponent {
  fun inject(activity: MainActivity)

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance appContext: Context): AppComponent
  }
}
