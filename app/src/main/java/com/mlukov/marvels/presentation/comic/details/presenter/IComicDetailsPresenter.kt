package com.mlukov.marvels.presentation.comic.details.presenter

import com.mlukov.marvels.mvp.IBasePresenter

interface IComicDetailsPresenter: IBasePresenter {

    fun loadComicDetails(refresh: Boolean, id:Long )
}