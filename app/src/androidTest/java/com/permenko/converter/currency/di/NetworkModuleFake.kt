package com.permenko.converter.currency.di

import com.permenko.converter.currency.network.CurrencyRatesApi
import com.permenko.converter.currency.network.CurrencyRatesApiFake
import com.permenko.converter.currency.network.CurrencyRatesApiFake.Companion.NETWORK_DELAY_MS
import dagger.Module
import dagger.Provides

@Module
class NetworkModuleFake {
  @Provides
  fun ratesApi(): CurrencyRatesApi = CurrencyRatesApiFake(NETWORK_DELAY_MS)
}
