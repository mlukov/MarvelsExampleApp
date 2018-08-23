package com.mlukov.marvels.domain.models

import java.io.Serializable

class ComicList () {

    var comics:MutableList<Comic> = mutableListOf<Comic>()

    constructor( list: List<Comic>) : this(){

        comics.addAll( list )
    }

    companion object {

        fun empty() : ComicList {

            val emptyData = ComicList()
            emptyData.comics = mutableListOf<Comic>()
            return emptyData
        }
    }

}