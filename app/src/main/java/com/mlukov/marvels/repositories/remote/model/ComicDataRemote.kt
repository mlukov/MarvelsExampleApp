package com.mlukov.marvels.repositories.remote.model

import com.google.gson.annotations.SerializedName

class ComicDataRemote {

    @SerializedName("count")
    var count: Int = 0
        private set

    @SerializedName("total")
    var total: Int =0
        private set

    @SerializedName("results")
    var results: List<ComicRemote>? = null
        private set
}