package com.news.app.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.news.app.model.Detail
import com.news.app.model.DetailResponse
import com.news.app.retrofit.ApiClient
import com.news.app.retrofit.ApisInterface
import com.news.app.retrofit.DaggerApiClientComponent
import com.news.app.retrofit.NetModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class DetailViewModel(var kids: ArrayList<Int>) : ViewModel() {
    // TODO: Implement the ViewModel

    private var retrofit: Retrofit? = null
    private var apiClient: ApiClient? = null
    private var apiInterface: ApisInterface? = null
    internal var list = ArrayList<Detail>()

    private lateinit var detailList: MutableLiveData<ArrayList<Detail>>
    var isLoading = MutableLiveData<Boolean>()
    var apiError = MutableLiveData<Throwable>()

    fun getDetail(): LiveData<ArrayList<Detail>> {

        val component = DaggerApiClientComponent.builder().netModule(NetModule()).build()
        apiClient = component.provideRetrofit()
        retrofit = apiClient?.retrofit
        apiInterface = retrofit?.create<ApisInterface>(ApisInterface::class.java)

        if (!::detailList.isInitialized) {
            detailList = MutableLiveData()
            loadDetail()
        }
        return detailList
    }

    private fun loadDetail() {
        isLoading.value = true
        for (i in kids!!.indices) {
            apiInterface?.getDetail(kids.get(i))?.enqueue(object : Callback<DetailResponse> {
                override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                    isLoading.value = false
                    if (response.isSuccessful) {
                        var title = response.body()?.text.toString()
                        var name = response.body()?.by.toString()
                        val time = response.body()?.time

                        var currentTime = System.currentTimeMillis() / 1000L
                        if(time != null) {
                            var result = convertFromDuration(currentTime - time!!)
                            list.add(Detail(title, name, result.toString()))
                        }else{
                            list.add(Detail(title, name, ""))
                        }

                        detailList.postValue(list)
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    isLoading.value = false
                    apiError.value = t.cause
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


