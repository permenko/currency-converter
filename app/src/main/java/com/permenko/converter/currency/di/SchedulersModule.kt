package com.permenko.converter.currency.di

import com.permenko.converter.currency.utils.SchedulersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulersModule {
  @Provides
  @Singleton
  fun schedulers() = object : SchedulersProvider {}
}
