package com.mlukov.marvels.di.modules

import com.mlukov.marvels.di.annotations.ActivityScope
import com.mlukov.marvels.presentation.articles.details.di.ArticleDetailsFragmentModule
import com.mlukov.marvels.presentation.articles.list.di.ArticlesListFragmentModule
import com.mlukov.marvels.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface BuildModule {

    @ContributesAndroidInjector(modules = arrayOf(ActivityModule::class, ArticlesListFragmentModule::class, ArticleDetailsFragmentModule::class))
    @ActivityScope
    fun bindsMainActivity(): MainActivity
}