package com.mlukov.marvels.domain.repositories.local

import com.mlukov.marvels.domain.models.Comic
import com.mlukov.marvels.domain.models.ComicList
import io.reactivex.Single

interface IComicsLocalRepository {

    fun getComicList() : Single<ComicList>
    fun updateComicList(comicList : ComicList) : Single<ComicList>

    fun getComic( id: Long ) : Single<Comic>
    fun updateComic(comic : Comic) : Single<Comic>
}