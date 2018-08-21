package com.mlukov.marvels.presentation.articles.details.view

import com.mlukov.marvels.presentation.articles.details.model.ArticleDetailsViewData

interface IArticleDetailsView {

    fun onDetailsLoaded( articleDetailsViewData: ArticleDetailsViewData )

    fun onLoadingStateChange( isLoading : Boolean )

    fun onError( errorMessage: String)
}