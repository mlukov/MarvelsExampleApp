package com.mlukov.marvels.repositories.remote.model

import com.google.gson.annotations.SerializedName

class ComicRemote {

    @SerializedName("id")
    var id: Long? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("detailLink")
    var description: String? = null

    @SerializedName("thumbnail")
    var thumbnail: ComicImage? = null

}