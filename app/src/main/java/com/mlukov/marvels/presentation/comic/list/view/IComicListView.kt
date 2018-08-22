package com.mlukov.marvels.presentation.comic.list.view

import com.mlukov.marvels.presentation.comic.list.model.ComicListViewModel


interface IComicListView {

    fun onLoadingStateChange(isLoading : Boolean );

    fun onArticlesLoaded(comicListViewModel : ComicListViewModel )

    fun onError( errorMessage: String)
}
