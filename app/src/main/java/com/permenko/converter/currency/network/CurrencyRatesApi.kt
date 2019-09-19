package com.permenko.converter.currency.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRatesApi {
  @GET("latest")
  fun rates(@Query("base") base: String?): Single<CurrencyRatesResponse>
}
