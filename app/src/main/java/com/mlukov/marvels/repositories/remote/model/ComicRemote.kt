package com.mlukov.marvels.repositories.remote.model

import com.google.gson.annotations.SerializedName

class ComicRemote {

    @SerializedName("id")
    var id: Long? = null
    private set

    @SerializedName("title")
    var title: String? = null
        private set

    @SerializedName("detailLink")
    var description: String? = null
        private set

    @SerializedName("thumbnail")
    var thumbnail: ComicImage? = null
        private set
}