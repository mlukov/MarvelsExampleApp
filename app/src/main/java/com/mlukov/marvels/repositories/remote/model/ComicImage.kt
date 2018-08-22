package com.mlukov.marvels.repositories.remote.model

import com.google.gson.annotations.SerializedName

class ComicImage {
    
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

    val url : String get()= "$path.$extension"
}