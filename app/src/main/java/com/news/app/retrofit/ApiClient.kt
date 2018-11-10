package com.news.app.retrofit

import dagger.Module
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Ankit on 18/06/2017.
 */
@Module
class ApiClient @Inject
constructor() {

    var retrofit: Retrofit
        internal set

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder().baseUrl(HttpConstant.BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build()
    }

}
