package com.news.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ankit on 07/11/2018.
 */

class DetailResponse {

    @SerializedName("by")
    @Expose
    var by: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("time")
    @Expose
    var time: Int? = null
    @SerializedName("text")
    @Expose
    var text: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null

}

