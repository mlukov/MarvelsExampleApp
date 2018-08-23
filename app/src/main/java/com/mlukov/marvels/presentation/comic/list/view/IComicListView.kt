package com.mlukov.marvels.presentation.comic.list.view

import com.mlukov.marvels.presentation.comic.list.model.ComicListViewModel


interface IComicListView {

    fun onLoadingStateChange(isLoading : Boolean );

    fun onComicsLoaded(comicListViewModel : ComicListViewModel )

    fun onError( errorMessage: String)
}
