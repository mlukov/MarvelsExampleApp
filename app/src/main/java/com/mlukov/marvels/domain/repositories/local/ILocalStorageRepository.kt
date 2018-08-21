package com.mlukov.marvels.domain.repositories.local

import com.mlukov.marvels.domain.models.ArticleData
import com.mlukov.marvels.domain.models.ArticleDataList
import io.reactivex.Single

interface ILocalStorageRepository {

    fun dropArticleDataListCache()
    fun getArticleDataListFromCache() : Single<ArticleDataList>
    fun addArticleDataListToCache( articleDataList : ArticleDataList) : Single<ArticleDataList>
}