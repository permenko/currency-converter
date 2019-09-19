package com.permenko.converter.currency.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulersProvider {
  val io: Scheduler get() = Schedulers.io()
  val main: Scheduler get() = AndroidSchedulers.mainThread()
}
