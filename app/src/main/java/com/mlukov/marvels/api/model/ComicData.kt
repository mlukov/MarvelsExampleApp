package com.mlukov.marvels.api.model

import com.google.gson.annotations.SerializedName

class ComicData {

    @SerializedName("count")
    var count: Int = 0
        private set

    @SerializedName("total")
    var total: Int =0
        private set

    @SerializedName("results")
    var results: List<Comic>? = null
        private set


}