package com.mlukov.marvels.repositories.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class LocalComics
    constructor(
            @PrimaryKey
            var id: Long =0,

            var title: String = "",

            var description:String = "",

            var thumbUrl: String = ""){
}