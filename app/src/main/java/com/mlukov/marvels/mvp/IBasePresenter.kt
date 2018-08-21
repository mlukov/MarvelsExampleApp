package com.mlukov.marvels.mvp

import io.reactivex.disposables.Disposable

interface IBasePresenter {

    fun dispose()

    fun add(disposable : Disposable)
}