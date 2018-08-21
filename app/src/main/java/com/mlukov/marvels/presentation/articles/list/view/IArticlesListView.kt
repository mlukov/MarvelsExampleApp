package com.mlukov.marvels.presentation.articles.list.view

import com.mlukov.marvels.presentation.articles.list.model.ArticleListViewModel


interface IArticlesListView {

    fun onLoadingStateChange(isLoading : Boolean );

    fun onArticlesLoaded( articleListViewModel : ArticleListViewModel )

    fun onError( errorMessage: String)
}
