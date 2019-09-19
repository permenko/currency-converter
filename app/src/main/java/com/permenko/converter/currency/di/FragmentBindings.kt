package com.permenko.converter.currency.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.permenko.converter.currency.ui.navigation.NavigationFragment
import com.permenko.converter.currency.ui.rates.CurrencyRatesFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentBindings {
  @Binds
  fun fragmentFactory(fragmentFactory: ManualFragmentFactory): FragmentFactory

  @Binds
  @IntoMap
  @FragmentKey(NavigationFragment::class)
  fun fragmentNavigation(fragment: NavigationFragment): Fragment

  @Binds
  @IntoMap
  @FragmentKey(CurrencyRatesFragment::class)
  fun fragmentCurrencyRates(fragment: CurrencyRatesFragment): Fragment
}
