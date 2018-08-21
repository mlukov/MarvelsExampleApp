package com.mlukov.marvels.domain.interactors

import com.mlukov.marvels.domain.models.ArticleDataList
import com.mlukov.marvels.domain.models.ArticleDetailData
import io.reactivex.Completable
import io.reactivex.Single

interface IArticleInteractor {

    fun getArticleList() : Single<ArticleDataList>

    fun getArticleDetails( articleId: Int): Single<ArticleDetailData>

    fun clearCache() :Completable
}