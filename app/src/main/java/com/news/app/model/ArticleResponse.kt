package com.news.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ArticleResponse{

    @SerializedName("by")
    @Expose
    var by: String? = null
    @SerializedName("descendants")
    @Expose
    var descendants: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("kids")
    @Expose
    var kids: List<Int>? = null
    @SerializedName("score")
    @Expose
    var score: Int? = null
    @SerializedName("time")
    @Expose
    var time: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null

}