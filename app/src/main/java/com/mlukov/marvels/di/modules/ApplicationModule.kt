package com.mlukov.marvels.di.modules

import android.content.Context

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mlukov.marvels.ArticlesApp
import com.mlukov.marvels.di.annotations.AppContext
import com.mlukov.marvels.domain.interactors.ArticleInteractor
import com.mlukov.marvels.domain.interactors.IArticleInteractor
import com.mlukov.marvels.domain.repositories.articles.ArticlesApiRepository
import com.mlukov.marvels.domain.repositories.articles.IArticlesApiRepository
import com.mlukov.marvels.domain.repositories.local.ILocalStorageRepository
import com.mlukov.marvels.domain.repositories.local.LocalStorageRepository
import com.mlukov.marvels.utils.ISchedulersProvider
import com.mlukov.marvels.utils.SchedulersProvider

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    @Singleton
    @AppContext
    internal fun providesAppContext(articlesApp : ArticlesApp) : Context {

        return articlesApp
    }

    @Provides
    @Singleton
    internal fun providesGson() : Gson {
        return GsonBuilder()
                .setDateFormat("dd/mm/yyyy HH:mm")
                .create()
    }

    @Provides
    internal fun providesLocalStorageRepository(@AppContext context: Context, gson: Gson): ILocalStorageRepository {

        return LocalStorageRepository(context.cacheDir, gson)
    }

    @Provides
    internal fun providesSchedulersProvider(schedulersProvider: SchedulersProvider): ISchedulersProvider {

        return schedulersProvider
    }

    @Provides
    internal fun providesArticlesApiRepository( articlesApiRepository: ArticlesApiRepository): IArticlesApiRepository {

        return articlesApiRepository
    }

    @Provides
    fun providesArticleInteractor(articleInteractor: ArticleInteractor): IArticleInteractor {

        return articleInteractor
    }

}
