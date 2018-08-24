package com.mlukov.marvels.repositories.remote.model

import com.google.gson.annotations.SerializedName

data class ComicDataResponse
    constructor(
            @SerializedName("status")
            var status: String ?= null,

            @SerializedName("code")
            var code: Int ?= null,

            @SerializedName("data")
            var data: ComicDataRemote ?= null ){
}