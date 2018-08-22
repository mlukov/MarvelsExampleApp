package com.mlukov.marvels.di.modules

import com.mlukov.marvels.di.annotations.ActivityScope
import com.mlukov.marvels.presentation.comic.details.di.ComicDetailsFragmentModule
import com.mlukov.marvels.presentation.comic.list.di.ComicListFragmentModule
import com.mlukov.marvels.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface BuildModule {

    @ContributesAndroidInjector(modules = arrayOf(ActivityModule::class, ComicListFragmentModule::class, ComicDetailsFragmentModule::class))
    @ActivityScope
    fun bindsMainActivity(): MainActivity
}