package com.mlukov.marvels.presentation.articles.details.model

import com.mlukov.marvels.presentation.articles.list.model.ArticleViewData
import java.util.*
import java.io.Serializable


class ArticleDetailsViewData(  id :Int?,
                               title: String?,
                               subtitle: String?,
                               date: Date?,
                               val body: String? )
    : ArticleViewData( id, title, subtitle, date ), Serializable {

    constructor( ): this( null, null, null, null, null ){
    }
    constructor( articleViewData: ArticleViewData ): this( articleViewData.id, articleViewData.title, articleViewData.subtitle, articleViewData.date, null )
}