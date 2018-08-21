package com.mlukov.marvels.di.modules

import android.content.Context
import com.mlukov.marvels.presentation.providers.INetworkInfoProvider
import com.mlukov.marvels.presentation.providers.IResourceProvider
import com.mlukov.marvels.presentation.providers.NetworkInfoProvider
import com.mlukov.marvels.presentation.providers.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DeviceModule( val context : Context ){

    @Provides
    @Singleton
    fun providesNetworkInfoProvider() : INetworkInfoProvider{

        return NetworkInfoProvider( context )
    }

    @Provides
    @Singleton
    fun providesResourceProvider() :IResourceProvider{

        return ResourceProvider( context )
    }

}