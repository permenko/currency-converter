package com.permenko.converter.currency.network

import com.google.gson.annotations.SerializedName

data class CurrencyRatesResponse(
  @SerializedName("rates") val rates: Map<String, Float>,
  @SerializedName("base") val baseCurrency: String,
  @SerializedName("date") val date: String
)
