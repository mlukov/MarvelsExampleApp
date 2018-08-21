package com.mlukov.marvels.presentation.articles.details.presenter

import com.mlukov.marvels.mvp.IBasePresenter

interface IArticleDetailsPresenter: IBasePresenter {

    fun loadArticleDetails( articleId:Int )
}