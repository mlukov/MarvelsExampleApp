package com.mlukov.marvels.domain.repositories.articles


import com.mlukov.marvels.api.MarvelsApiController
import com.mlukov.marvels.api.model.ArticleDetailApiData
import com.mlukov.marvels.api.model.ArticleListApi

import javax.inject.Inject
import io.reactivex.Single


class ArticlesApiRepository
@Inject
constructor( internal var apiController : MarvelsApiController ) : IArticlesApiRepository {

    override fun getArticleList() : Single<ArticleListApi> {

        return apiController.getArticleList()
    }

    override fun getArticleDetails(articleId : Int) : Single<ArticleDetailApiData> {

        return apiController.getArticleDetails(articleId)
    }
}