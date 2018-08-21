package com.mlukov.marvels.presentation.articles.list.presenter

import com.mlukov.marvels.mvp.IBasePresenter

interface IArticlesListPresenter :IBasePresenter {

    fun loadArticles( refresh: Boolean )
}
