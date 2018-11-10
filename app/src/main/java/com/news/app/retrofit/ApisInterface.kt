package com.news.app.retrofit

import com.news.app.model.ArticleResponse
import com.news.app.model.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Ankit on 18/07/2017.
 */

interface ApisInterface {

    @GET("v0/topstories.json?print=pretty")
    abstract fun getTopStories(): Call<List<Int>>

    @GET("v0/item/{articleid}.json?print=pretty")
    abstract fun getArticle(@Path("articleid") id: Int): Call<ArticleResponse>

    @GET("v0/item/{articleid}.json?print=pretty")
    abstract fun getDetail(@Path("articleid") id: Int): Call<DetailResponse>

}