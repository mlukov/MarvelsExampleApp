package com.mlukov.marvels.api.model

import com.google.gson.annotations.SerializedName

class Image {
    
    @SerializedName("path")
    var path: String? = null
        private set

    @SerializedName("extension")
    var extension: String? = null
        private set

    constructor( path: String, extension: String) {

        this.path = path
        this.extension = extension
    }

    fun getUrl(): String? {
        return "$path.$extension"
    }
}