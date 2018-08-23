package com.mlukov.marvels.presentation.comic.list.presenter

import android.util.Log
import com.mlukov.marvels.R
import com.mlukov.marvels.domain.interactors.IComicInteractor
import com.mlukov.marvels.domain.models.Comic
import com.mlukov.marvels.domain.providers.ILogger
import com.mlukov.marvels.mvp.BasePresenter
import com.mlukov.marvels.presentation.comic.list.model.ComicListViewModel
import com.mlukov.marvels.presentation.comic.list.model.ComicViewData
import com.mlukov.marvels.presentation.providers.INetworkInfoProvider
import com.mlukov.marvels.presentation.providers.IResourceProvider
import com.mlukov.marvels.presentation.comic.list.view.IComicListView
import com.mlukov.marvels.utils.ISchedulersProvider


import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class ComicListPresenter @Inject
constructor(val comicView : IComicListView,
            val comicInteractor : IComicInteractor,
            val schedulersProvider : ISchedulersProvider,
            val resourceProvider : IResourceProvider,
            val networkInfoProvider : INetworkInfoProvider,
            val logger: ILogger) : BasePresenter(), IComicListPresenter {


    override fun loadComics(refresh: Boolean ) {
        add( getComicList( refresh ) )
    }

    private fun getComicList( refresh: Boolean) : Disposable{

        return comicInteractor.getComicList( refresh, LIST_LIMIT ).map {

                val comicsList = ComicListViewModel()

                for( comic in it.comics){

                    comicsList.list.add(createFrom(comic ))
                }
                return@map comicsList
            }
                .subscribeOn( schedulersProvider.ioScheduler())
                .observeOn( schedulersProvider.uiScheduler())
                .subscribe({

                    comicView.onComicsLoaded(it)
                    comicView.onLoadingStateChange(false )
                },
                        {

                            onError( throwable = it )
                        })

    }

    private fun onError( throwable : Throwable ){

        logger.e( TAG, throwable.message, throwable)

        comicView.onLoadingStateChange(false )

        var resId =
                if( !networkInfoProvider.isNetworkConnected )
                    R.string.internet_connection_offline
                else
                    R.string.oops_went_wrong_try

        comicView.onError(resourceProvider.getString(resId ) )
    }

    private fun createFrom(comic : Comic ) : ComicViewData{

        return ComicViewData(comic.id,
                             comic.title,
                             comic.thumbnailUrl,
                             comic.description)

    }

    companion object {

        private val TAG = ComicListPresenter::class.simpleName
        private val LIST_LIMIT = 20L
    }
}
