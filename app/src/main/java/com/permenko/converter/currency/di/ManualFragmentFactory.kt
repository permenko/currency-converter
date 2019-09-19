package com.permenko.converter.currency.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider

class ManualFragmentFactory @Inject constructor(
  private val providers: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {
  override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
    return providers.entries
      .find { classLoader.loadClass(className).isAssignableFrom(it.key) }
      ?.value?.get()
      ?: throw IllegalArgumentException("$className cannot be created by $this.")
  }
}
