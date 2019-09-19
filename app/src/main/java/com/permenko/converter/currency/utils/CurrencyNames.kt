package com.permenko.converter.currency.utils

class CurrencyNames {
  companion object {
    fun name(currency: String) = currencyNames[currency] ?: UNKNOWN_CURRENCY_NAME
  }
}

private val currencyNames = mapOf(
  "AUD" to "Australian Dollars",
  "BGN" to "Bulgarian Lev",
  "BRL" to "Brazilian Real",
  "CAD" to "Canadian Dollars",
  "CHF" to "Swiss Franc",
  "CNY" to "Chinese Yuan",
  "CZK" to "Czech Koruna",
  "DKK" to "Danish Krone",
  "EUR" to "Euro",
  "GBP" to "British Pound",
  "HKD" to "Hong Kong Dollar",
  "HRK" to "Croatian Kuna",
  "HUF" to "Hungarian Forint",
  "IDR" to "Indonesian Rupiah",
  "ISL" to "Israeli Shekel",
  "INR" to "Indian Rupee",
  "ISK" to "Icelandic Krona",
  "JPY" to "Japanese Yen",
  "KRW" to "South Korean Won",
  "MXN" to "Mexican Peso",
  "MYR" to "Malaysian Ringgit",
  "NOK" to "Norwegian Krone",
  "NZD" to "New Zealand Dollar",
  "PHP" to "Philippine Peso",
  "PLN" to "Polish Zloty",
  "RON" to "Romanian Leu",
  "RUB" to "Russian Ruble",
  "SEK" to "Swedish Krona",
  "SGD" to "Singapore Dollar",
  "THB" to "Thai Baht",
  "TRY" to "Turkish Lira",
  "USD" to "US Dollar",
  "ZAR" to "South African Rand"
)

private const val UNKNOWN_CURRENCY_NAME = "Unknown"
