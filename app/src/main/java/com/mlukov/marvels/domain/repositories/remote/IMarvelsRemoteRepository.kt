package com.mlukov.marvels.domain.repositories.remote

import com.mlukov.marvels.domain.models.Comic
import io.reactivex.Single

interface IMarvelsRemoteRepository {

    fun getComicsList(limit : Long ): Single<List<Comic>>

    fun getComicDetails(comicId: Long): Single<Comic>
}
