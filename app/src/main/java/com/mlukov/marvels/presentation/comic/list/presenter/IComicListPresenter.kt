package com.mlukov.marvels.presentation.comic.list.presenter

import com.mlukov.marvels.mvp.IBasePresenter

interface IComicListPresenter :IBasePresenter {

    fun loadComics(refresh: Boolean )
}
