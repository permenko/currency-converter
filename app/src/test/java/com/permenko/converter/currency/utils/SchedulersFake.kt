package com.permenko.converter.currency.utils

import com.permenko.converter.currency.utils.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class SchedulersFake : SchedulersProvider {
  override val io: Scheduler get() = Schedulers.trampoline()
  override val main: Scheduler get() = Schedulers.trampoline()
}
