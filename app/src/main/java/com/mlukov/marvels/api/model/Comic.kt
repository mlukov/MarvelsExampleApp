package com.mlukov.marvels.api.model

import com.google.gson.annotations.SerializedName

class Comic {

    @SerializedName("id")
    var id: Long? = null
    private set

    @SerializedName("title")
    var title: String? = null
        private set

    @SerializedName("description")
    var description: String? = null
        private set

    @SerializedName("pageCount")
    var pageCount: Int? = null
        private set

    @SerializedName("thumbnail")
    var thumbnail: Image? = null
        private set
}