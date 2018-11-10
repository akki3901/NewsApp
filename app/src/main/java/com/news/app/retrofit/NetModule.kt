package com.news.app.retrofit


import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Ankit on 18/07/2017.
 */
@Module
class NetModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(): ApiClient {
        return ApiClient()
    }
}
