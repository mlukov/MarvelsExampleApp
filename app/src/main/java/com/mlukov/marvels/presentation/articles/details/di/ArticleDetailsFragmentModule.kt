package com.mlukov.marvels.presentation.articles.details.di

import com.mlukov.marvels.presentation.articles.details.presenter.ArticleDetailsPresenter
import com.mlukov.marvels.presentation.articles.details.presenter.IArticleDetailsPresenter
import com.mlukov.marvels.presentation.articles.details.view.ArticleDetailsFragment
import com.mlukov.marvels.presentation.articles.details.view.IArticleDetailsView
import com.mlukov.marvels.presentation.articles.list.presenter.ArticlesListPresenter
import com.mlukov.marvels.presentation.articles.list.presenter.IArticlesListPresenter
import com.mlukov.marvels.presentation.articles.list.view.ArticlesListFragment
import com.mlukov.marvels.presentation.articles.list.view.IArticlesListView
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ArticleDetailsFragmentModule {

    @ContributesAndroidInjector
    fun bindArticleDetailsFragment(): ArticleDetailsFragment

    @Binds
    fun bindsArticleDetailsView( articleDetailsFragment: ArticleDetailsFragment): IArticleDetailsView

    @Binds
    fun bindArticleDetailsPresenter( articleDetailsPresenter: ArticleDetailsPresenter ): IArticleDetailsPresenter

}