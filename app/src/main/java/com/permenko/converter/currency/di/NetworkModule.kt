package com.permenko.converter.currency.di

import com.permenko.converter.currency.BuildConfig
import com.permenko.converter.currency.network.CurrencyRatesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
  @Provides
  @Singleton
  fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) BODY else NONE })
    .build()

  @Provides
  @Singleton
  fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl("https://api.exchangeratesapi.io/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  @Provides
  @Singleton
  fun ratesApi(retrofit: Retrofit): CurrencyRatesApi = retrofit.create(CurrencyRatesApi::class.java)
}
