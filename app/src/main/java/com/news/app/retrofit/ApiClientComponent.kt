package com.news.app.retrofit

import dagger.Component
import javax.inject.Singleton

/**
 * Created by Ankit on 18/07/2017.
 */
@Singleton
@Component(modules = arrayOf(NetModule::class))
interface ApiClientComponent {
    fun provideRetrofit(): ApiClient
}
