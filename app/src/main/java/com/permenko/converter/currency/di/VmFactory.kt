package com.permenko.converter.currency.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject
import javax.inject.Provider

inline fun <reified VM : ViewModel> Fragment.vm(factory: VmFactory<VM>): Lazy<VM> =
  lazy { ViewModelProviders.of(this, factory).get(VM::class.java) }

class VmFactory<VM : ViewModel> @Inject constructor(private val vm: Provider<VM>) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST") override fun <T : ViewModel?> create(modelClass: Class<T>): T = vm.get() as T
}
