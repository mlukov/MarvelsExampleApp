package com.mlukov.marvels.di.modules

import com.mlukov.marvels.di.annotations.ActivityScope
import com.mlukov.marvels.presentation.main.IMainNavigator
import com.mlukov.marvels.presentation.main.MainActivity
import dagger.Binds
import dagger.Module

@Module
interface ActivityModule {

    @Binds
    @ActivityScope
    fun bindsMainNavigator(mainActivity: MainActivity): IMainNavigator

}