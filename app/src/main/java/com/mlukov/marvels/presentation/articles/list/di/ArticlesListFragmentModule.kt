package com.mlukov.marvels.presentation.articles.list.di

import com.mlukov.marvels.presentation.articles.list.presenter.ArticlesListPresenter
import com.mlukov.marvels.presentation.articles.list.presenter.IArticlesListPresenter
import com.mlukov.marvels.presentation.articles.list.view.ArticlesListFragment
import com.mlukov.marvels.presentation.articles.list.view.IArticlesListView
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ArticlesListFragmentModule {

    @ContributesAndroidInjector
    fun bindArticleListFragment(): ArticlesListFragment

    @Binds
    fun bindsArticlesListView(articlesListFragment: ArticlesListFragment ): IArticlesListView

    @Binds
    fun bindArticlesListPresenter( articlesListPresenter: ArticlesListPresenter): IArticlesListPresenter

}