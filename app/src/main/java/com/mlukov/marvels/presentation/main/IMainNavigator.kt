package com.mlukov.marvels.presentation.main

import com.mlukov.marvels.presentation.articles.details.model.ArticleDetailsViewData

interface IMainNavigator {

        fun showArticleList()

        fun showArticleDetails( articleDetailsViewData: ArticleDetailsViewData )
}