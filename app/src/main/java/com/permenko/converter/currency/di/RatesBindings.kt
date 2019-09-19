package com.permenko.converter.currency.di

import com.permenko.converter.currency.ui.rates.CurrencyRatesRepository
import com.permenko.converter.currency.ui.rates.CurrencyRatesRepositoryReal
import dagger.Binds
import dagger.Module

@Module
interface RatesBindings {
  @Binds
  fun ratesRepository(repository: CurrencyRatesRepositoryReal): CurrencyRatesRepository
}
