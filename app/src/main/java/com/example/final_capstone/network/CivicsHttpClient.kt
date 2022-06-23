package com.example.android.politicalpreparedness.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class CivicsHttpClient : OkHttpClient() {

  companion object {
    val interceptor = HttpLoggingInterceptor().apply {
      this.level = HttpLoggingInterceptor.Level.BODY
    };
    const val API_KEY = "AIzaSyBt-oaQGsUNii3Pspnu-TMEGHyr9g0QJyI"

    fun getClient(): OkHttpClient {
      val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
      }

      return Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
          val original = chain.request()
          val url = original
            .url
            .newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()
          val request = original
            .newBuilder()
            .url(url)
            .build()
          chain.proceed(request)

        }
        .build()
    }

  }

}
