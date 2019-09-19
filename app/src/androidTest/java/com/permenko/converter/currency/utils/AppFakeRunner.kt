package com.permenko.converter.currency.utils

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.permenko.converter.currency.AppFake

class AppFakeRunner : AndroidJUnitRunner() {
  override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application =
    super.newApplication(cl, AppFake::class.java.name, context)
}
