package com.mlukov.marvels.presentation.comic.list.di

import com.mlukov.marvels.presentation.comic.list.presenter.ComicListPresenter
import com.mlukov.marvels.presentation.comic.list.presenter.IComicListPresenter
import com.mlukov.marvels.presentation.comic.list.view.ComicListFragment
import com.mlukov.marvels.presentation.comic.list.view.IComicListView
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ComicListFragmentModule {

    @ContributesAndroidInjector
    fun bindComicListFragment(): ComicListFragment

    @Binds
    fun bindsComicsListView(comicListFragment: ComicListFragment ): IComicListView

    @Binds
    fun bindComicsListPresenter( comicsListPresenter: ComicListPresenter): IComicListPresenter

}