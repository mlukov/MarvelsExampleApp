package com.mlukov.marvels.domain.interactors

import com.mlukov.marvels.domain.models.Comic
import com.mlukov.marvels.domain.models.ComicList
import com.mlukov.marvels.domain.repositories.remote.IMarvelsRemoteRepository
import com.mlukov.marvels.domain.repositories.local.IComicsLocalRepository
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import javax.inject.Inject

open class ComicInteractor
@Inject constructor(val marvelsRemoteRepository : IMarvelsRemoteRepository,
                    val comicsLocalRepository : IComicsLocalRepository) : IComicInteractor{


    override fun getComicList( refresh: Boolean, limit : Long  ) : Single<ComicList> {

        return loadComicList( refresh, limit ).first( ComicList())
    }

    override fun getComic(refresh: Boolean, id : Long) : Single<Comic> {

        if( refresh )
            return getComicDetailFromServer( id )

        return comicsLocalRepository.getComic( id)
                .concatWith( getComicDetailFromServer( id ) )
                .filter( Predicate<Comic?> { comic-> comic.id != 0L
                }).first( Comic() )
    }


    //region private methods
    private fun loadComicList( refresh: Boolean, limit: Long ) : Flowable<ComicList> {

        if( refresh )
            return getComicListFromServer( limit ).toFlowable()

        return comicsLocalRepository.getComicList()
                .concatWith( getComicListFromServer( limit ) )
                .filter( Predicate<ComicList> { comicDataList->
                    (comicDataList.comics.isEmpty() == false )
                })
    }

    private fun getComicListFromServer( limit : Long ) : Single<ComicList> {

        return marvelsRemoteRepository.getComicsList( limit )
                .flatMap(object : Function<List<Comic>, SingleSource<ComicList>> {
                    @Throws(Exception::class)
                    override fun apply( comicList : List<Comic>) : SingleSource<ComicList> {

                        val list = ComicList()
                        list.comics.addAll( comicList )

                        return comicsLocalRepository.updateComicList( list )
                    }
                })
    }


    private fun getComicDetailFromServer( id : Long ) : Single<Comic> {

        return marvelsRemoteRepository.getComicDetails( id )
                .flatMap(object : Function<Comic, SingleSource<Comic>> {
                    @Throws(Exception::class)
                    override fun apply( comic : Comic) : SingleSource<Comic> {

                        return comicsLocalRepository.updateComic( comic )
                    }
                })
    }
}