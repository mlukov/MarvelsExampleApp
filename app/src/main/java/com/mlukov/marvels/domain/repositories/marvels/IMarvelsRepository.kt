package com.mlukov.marvels.domain.repositories.marvels

import com.mlukov.marvels.api.model.ArticleDetailApiData
import com.mlukov.marvels.api.model.ArticleListApi
import io.reactivex.Single

interface IMarvelsRepository {

    // callback executed on main thread
    fun getArticleList(): Single<ArticleListApi>

    // callback executed on main thread
    fun getArticleDetails(articleId: Int): Single<ArticleDetailApiData>
}
