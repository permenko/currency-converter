package com.permenko.converter.currency.utils

import com.jraska.livedata.TestObserver

fun <T> TestObserver<T>.assertOneValue(): TestObserver<T> = this.assertHistorySize(1)

fun <T> TestObserver<T>.assertOneValue(expected: T): TestObserver<T> = this.assertValue(expected).assertHistorySize(1)
