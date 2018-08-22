package com.mlukov.marvels.domain.interactors

import com.mlukov.marvels.domain.models.Comic
import com.mlukov.marvels.domain.models.ComicList
import io.reactivex.Completable
import io.reactivex.Single

interface IComicInteractor {

    fun getComicList( refresh: Boolean, limit: Long ) : Single<ComicList>

    fun getComic( refresh: Boolean, id: Long ): Single<Comic>
}