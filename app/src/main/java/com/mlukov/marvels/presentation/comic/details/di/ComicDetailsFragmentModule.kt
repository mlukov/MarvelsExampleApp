package com.mlukov.marvels.presentation.comic.details.di

import com.mlukov.marvels.presentation.comic.details.presenter.ComicDetailsPresenter
import com.mlukov.marvels.presentation.comic.details.presenter.IComicDetailsPresenter
import com.mlukov.marvels.presentation.comic.details.view.ComicDetailsFragment
import com.mlukov.marvels.presentation.comic.details.view.IComicDetailsView
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ComicDetailsFragmentModule {

    @ContributesAndroidInjector
    fun bindDetailsFragment(): ComicDetailsFragment

    @Binds
    fun bindsDetailsView( detailsFragment: ComicDetailsFragment): IComicDetailsView

    @Binds
    fun bindDetailsPresenter( detailsPresenter: ComicDetailsPresenter ): IComicDetailsPresenter

}