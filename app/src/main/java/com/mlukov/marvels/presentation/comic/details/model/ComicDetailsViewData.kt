package com.mlukov.marvels.presentation.comic.details.model

import android.accounts.AuthenticatorDescription
import com.mlukov.marvels.presentation.comic.list.model.ComicViewData
import java.util.*
import java.io.Serializable


class ComicDetailsViewData(id :Long?,
                           title: String?,
                           thumbUlr: String?,
                           description:String? )
    : ComicViewData(id, title, thumbUlr, description ), Serializable {

    constructor( ): this(  null, null, null, null ){
    }
    constructor(comicViewData: ComicViewData ): this(comicViewData.id, comicViewData.title, comicViewData.thumbUlr,comicViewData.description )
}