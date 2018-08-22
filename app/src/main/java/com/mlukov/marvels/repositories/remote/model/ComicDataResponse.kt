package com.mlukov.marvels.repositories.remote.model

import com.google.gson.annotations.SerializedName

class ComicDataResponse {

    @SerializedName("status")
    var status: String? = null
        private set

    @SerializedName("code")
    var code: Int?= null
        private set

    @SerializedName("data")
    var data: ComicDataRemote? = null
        private set
}