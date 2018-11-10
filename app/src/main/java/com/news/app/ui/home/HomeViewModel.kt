package com.news.app.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.news.app.model.Article
import com.news.app.model.ArticleResponse
import com.news.app.retrofit.ApiClient
import com.news.app.retrofit.ApisInterface
import com.news.app.retrofit.DaggerApiClientComponent
import com.news.app.retrofit.NetModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by Ankit on 07/11/2018.
 */
class HomeViewModel : ViewModel() {
    private var retrofit: Retrofit? = null
    private var apiClient: ApiClient? = null
    private var apiInterface: ApisInterface? = null
    internal var list = ArrayList<Article>()
    var articleIdList = listOf<Int>()

    private lateinit var articleList: MutableLiveData<ArrayList<Article>>
    var isLoading = MutableLiveData<Boolean>()
    var apiError = MutableLiveData<Throwable>()

    fun getTopics(): LiveData<ArrayList<Article>> {

        val component = DaggerApiClientComponent.builder().netModule(NetModule()).build()
        apiClient = component.provideRetrofit()
        retrofit = apiClient?.retrofit
        apiInterface = retrofit?.create<ApisInterface>(ApisInterface::class.java)

        if (!::articleList.isInitialized) {
            articleList = MutableLiveData()
            loadArticleIds()
        }
        return articleList
    }

    private fun loadArticleIds() {
        isLoading.value = true
        apiInterface?.getTopStories()?.enqueue(object : Callback<List<Int>> {
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                if (response.isSuccessful) {
                    isLoading.value = false
                    articleIdList = response.body()!!
                    loadTopics()
                }
            }

            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                isLoading.value = false
                apiError.value = t.cause
            }
        })
    }

    private fun loadTopics() {
        //for (i in articleIdList!!.indices) {    for (i in articleIdList.indices) {
        for (i in 0..50) {
            apiInterface?.getArticle(articleIdList!![i])?.enqueue(object : Callback<ArticleResponse> {
                override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                    if (response.isSuccessful) {
                        var title = response.body()?.title.toString()
                        var score = response.body()?.score.toString()
                        var name = response.body()?.by.toString()
                        val time = response.body()?.time

                        val kidsList = ArrayList<Int>()

                        var kidsData = response.body()?.kids
                        if (kidsData != null) {
                            for (j in kidsData!!.indices) {
                                kidsList.add(kidsData[j])
                            }
                        }

                        var currentTime = System.currentTimeMillis() / 1000L
                        if(time != null) {
                            var result = convertFromDuration(currentTime - time!!)
                            list.add(Article(title, score, name, result.toString(), kidsList))
                        }else{
                            list.add(Article(title, score, name, "", kidsList))
                        }

                        articleList.postValue(list)

                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {

                }
            })
        }
    }
}

fun convertFromDuration(timeInSeconds: Long): TimeInHours {
    var time = timeInSeconds
    val hours = time / 3600
    time %= 3600
    val minutes = time / 60
    time %= 60
    val seconds = time
    return TimeInHours(hours.toInt(), minutes.toInt(), seconds.toInt())
}

class TimeInHours(val hours: Int, val minutes: Int, val seconds: Int) {
    override fun toString(): String {

        when {
            hours == 0 -> return String.format("%d minutes", minutes, seconds)
            minutes == 0 -> return String.format("%d seconds", seconds)
            else -> return String.format("%d hours", hours)
        }
    }
}
