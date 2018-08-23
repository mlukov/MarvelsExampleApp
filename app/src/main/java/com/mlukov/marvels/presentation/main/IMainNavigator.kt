package com.mlukov.marvels.presentation.main

import android.view.View
import com.mlukov.marvels.presentation.comic.details.model.ComicDetailsViewData

interface IMainNavigator {

        fun showComicList()

        fun showComicDetails(comicDetailsViewData: ComicDetailsViewData)
}