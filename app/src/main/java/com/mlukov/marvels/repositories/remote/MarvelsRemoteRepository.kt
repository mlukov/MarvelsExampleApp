package com.mlukov.marvels.repositories.remote


import com.mlukov.marvels.BuildConfig
import com.mlukov.marvels.repositories.remote.model.ComicRemote
import com.mlukov.marvels.domain.calculators.IHashCalculator
import com.mlukov.marvels.domain.models.Comic
import com.mlukov.marvels.domain.providers.ITimestampProvider
import com.mlukov.marvels.domain.repositories.remote.IMarvelsRemoteRepository

import javax.inject.Inject
import io.reactivex.Single


class MarvelsRemoteRepository
@Inject
constructor(private var apiController : MarvelsApiController,
            private var hashCalculator: IHashCalculator,
            private val timestampProvider: ITimestampProvider) : IMarvelsRemoteRepository {

    private val privateKey = BuildConfig.PRIVATE_KEY
    private val publicKey = BuildConfig.PUBLIC_KEY

    override fun getComicsList(limit: Long ) : Single<List<Comic>> {

        val timeStamp = timestampProvider.getTimeStamp()
        return apiController.getComics( timeStamp, publicKey, calculateHash(timeStamp), limit )
                .map{

                    comicDataResponse ->
                    val list = mutableListOf<Comic>()

                    if( comicDataResponse .data?.results?.size == 0)
                        return@map list

                    for( comic in comicDataResponse.data?.results?.sortedByDescending { it-> it.id }.orEmpty() )
                        list.add( createFrom( comic ))

                    return@map list
                }
    }

    override fun getComicDetails(comicId : Long) : Single<Comic> {

        val timeStamp = timestampProvider.getTimeStamp()
        val hash = calculateHash(timeStamp)
        return apiController.getComicDetails(comicId, timeStamp, publicKey, hash )
                .map{

                    comicDataResponse -> createFrom( comicDataResponse.data?.results?.get( 0 ) ?: ComicRemote() )
                }
    }

    private fun calculateHash(timeStamp: String): String {

        return hashCalculator.calculate(timeStamp, privateKey, publicKey)
    }

    private fun createFrom( comicRemote: ComicRemote ) : Comic {

        val comic = Comic()
        comic.id = comicRemote.id
        comic.title = comicRemote.title
        comic.description = comicRemote.description
        comic.thumbnailUrl = comicRemote.thumbnail?.url

        return  comic
    }
}