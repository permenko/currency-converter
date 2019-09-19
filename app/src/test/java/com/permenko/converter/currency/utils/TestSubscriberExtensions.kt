package com.permenko.converter.currency.utils

import io.reactivex.observers.BaseTestConsumer.TestWaitStrategy
import io.reactivex.subscribers.TestSubscriber

fun <T> TestSubscriber<T>.awaitCount(atLeast: Int, timeoutMillis: Long): TestSubscriber<T> =
  awaitCount(atLeast, TestWaitStrategy.SLEEP_10MS, timeoutMillis)
