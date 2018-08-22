package com.mlukov.marvels.repositories.local

import com.mlukov.marvels.domain.models.Comic
import com.mlukov.marvels.domain.models.ComicList
import com.mlukov.marvels.domain.repositories.local.IComicsLocalRepository
import com.mlukov.marvels.repositories.local.model.LocalComics
import io.reactivex.Single
import javax.inject.Inject

class ComicsLocalRepository @Inject
constructor(private val database:MarvelsDatabase ) : IComicsLocalRepository {

    companion object {

        private val TAG = ComicsLocalRepository::class.simpleName
    }

    override fun getComicList(): Single<ComicList> {

        return database.daoAccess().getAllComics().map { createFrom( it) }
    }

    override fun updateComicList(comicList: ComicList): Single<ComicList> {

        for( comic in comicList.comics){

            var local :LocalComics? =null
            try{
                local = database.daoAccess().getComicById( comic.id ?:0 )
            }
            catch ( ex: Exception){}

            val updated = createFrom( comic )
            if ( local == null )
                database.daoAccess().insertComic( updated )
            else
                database.daoAccess().updateComic( updated )
        }

        return Single.just( comicList )
    }

    override fun getComic(id: Long): Single<Comic> {

        return database.daoAccess().getComicByIdAsync( id ).map { createFrom( it ) }
    }

    override fun updateComic(comic: Comic): Single<Comic> {
        return Single.just( comic )
    }

    private fun createFrom(localComics: List<LocalComics>) : ComicList{

        val comicList = ComicList()
        for( localComic in localComics )
            comicList.comics.add( createFrom( localComic ) )
        return  comicList
    }

    private fun createFrom(localComics: LocalComics) : Comic {

        val comic = Comic()
        comic.id = localComics.id
        comic.title = localComics.title
        comic.description = localComics.description
        comic.thumbnailUrl = localComics.thumbUrl

        return  comic
    }

    private fun createFrom(comic: Comic) :LocalComics  {

        val localComic = LocalComics()
        localComic.id = comic.id ?:0L
        localComic.title = comic.title ?:""
        localComic.description = comic.description ?:""
        localComic.thumbUrl = comic.thumbnailUrl?:""

        return  localComic
    }
}