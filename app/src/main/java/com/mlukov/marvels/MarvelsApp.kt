package com.mlukov.marvels

import android.app.Activity
import android.app.Application
import com.mlukov.marvels.di.components.DaggerApplicationComponent

import com.mlukov.marvels.di.modules.DeviceModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MarvelsApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {

        super.onCreate()

        DaggerApplicationComponent.builder()
                .application( this )
                .device( DeviceModule( this ) )
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
       return dispatchingAndroidInjector
    }
}
