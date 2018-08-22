package com.mlukov.marvels.presentation.comic.details.view

import com.mlukov.marvels.presentation.comic.details.model.ComicDetailsViewData

interface IComicDetailsView {

    fun onDetailsLoaded(comicDetailsViewData: ComicDetailsViewData )

    fun onLoadingStateChange( isLoading : Boolean )

    fun onError( errorMessage: String)
}