package com.mlukov.marvels.di.components


import com.mlukov.marvels.MarvelsApp
import com.mlukov.marvels.di.modules.*

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(
        modules = arrayOf(ApplicationModule::class,
                ActivityModule::class,
                DeviceModule::class,
                NetworkModule::class,
                BuildModule::class)
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app : MarvelsApp) : Builder
        //fun network(networkModule : NetworkModule) : Builder
        fun device( deviceModule : DeviceModule) : Builder
        fun build(): ApplicationComponent
    }

    fun inject(application : MarvelsApp)
}
