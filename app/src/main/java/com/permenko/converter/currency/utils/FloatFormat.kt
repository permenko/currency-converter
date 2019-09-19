package com.permenko.converter.currency.utils

fun Float.round(size: Int) = "%.${size}f".format(this)
