package com.mlukov.marvels.presentation.comic.details.presenter

import android.util.Log
import com.mlukov.marvels.R
import com.mlukov.marvels.domain.interactors.IComicInteractor
import com.mlukov.marvels.domain.models.Comic
import com.mlukov.marvels.mvp.BasePresenter
import com.mlukov.marvels.presentation.comic.details.model.ComicDetailsViewData
import com.mlukov.marvels.presentation.comic.details.view.IComicDetailsView
import com.mlukov.marvels.presentation.providers.INetworkInfoProvider
import com.mlukov.marvels.presentation.providers.IResourceProvider
import com.mlukov.marvels.utils.ISchedulersProvider


import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ComicDetailsPresenter @Inject
constructor(val comicDetailsView : IComicDetailsView,
            val comicInteractor : IComicInteractor,
            val schedulersProvider : ISchedulersProvider,
            val resourceProvider : IResourceProvider,
            val networkInfoProvider : INetworkInfoProvider) : BasePresenter(), IComicDetailsPresenter{


    override fun loadComicDetails(refresh: Boolean, id:Long ) {

        add(getComicDetails(refresh, id ) )
    }

    private fun getComicDetails(refresh: Boolean, id:Long ) : Disposable{

        return comicInteractor.getComic( refresh, id ).map {

                return@map createFrom( it )
            }
                .subscribeOn( schedulersProvider.ioScheduler())
                .observeOn( schedulersProvider.uiScheduler())
                .subscribe({

                    comicDetailsView.onDetailsLoaded(it )
                    comicDetailsView.onLoadingStateChange(false )
                },
                        {

                            onError( throwable = it )
                        })

    }

    private fun onError( throwable : Throwable ){

        Log.e( TAG, throwable.message, throwable)

        comicDetailsView.onLoadingStateChange(false )

        var resId =
                if( !networkInfoProvider.isNetworkConnected )
                    R.string.internet_connection_offline
                else
                    R.string.oops_went_wrong_try

        comicDetailsView.onError(resourceProvider.getString(resId ) )
    }

    private fun createFrom(comic : Comic) : ComicDetailsViewData{

        return ComicDetailsViewData(comic.id,
                                    comic.title,
                                    comic.thumbnailUrl,
                                    comic.description)
    }

    companion object {

        private val TAG = ComicDetailsPresenter::class.simpleName
    }
}
