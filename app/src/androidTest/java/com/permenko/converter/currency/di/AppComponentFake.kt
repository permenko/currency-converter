package com.permenko.converter.currency.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
  modules = [
    NetworkModuleFake::class,
    SchedulersModule::class,
    FragmentBindings::class,
    RatesBindings::class
  ]
)
@Singleton
interface AppComponentFake : AppComponent {
  @Component.Factory
  interface Factory {
    fun create(@BindsInstance appContext: Context): AppComponentFake
  }
}
