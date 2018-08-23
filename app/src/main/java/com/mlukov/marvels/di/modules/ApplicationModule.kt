package com.mlukov.marvels.di.modules

import android.content.Context

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mlukov.marvels.MarvelsApp
import com.mlukov.marvels.di.annotations.AppContext
import com.mlukov.marvels.domain.calculators.HashCalculator
import com.mlukov.marvels.domain.calculators.IHashCalculator
import com.mlukov.marvels.domain.interactors.ComicInteractor
import com.mlukov.marvels.domain.interactors.IComicInteractor
import com.mlukov.marvels.domain.providers.ITimestampProvider
import com.mlukov.marvels.domain.providers.TimestampProvider
import com.mlukov.marvels.repositories.remote.MarvelsRemoteRepository
import com.mlukov.marvels.domain.repositories.remote.IMarvelsRemoteRepository
import com.mlukov.marvels.domain.repositories.local.IComicsLocalRepository
import com.mlukov.marvels.repositories.local.ComicsLocalRepository
import com.mlukov.marvels.repositories.local.MarvelsDatabase
import com.mlukov.marvels.utils.ISchedulersProvider
import com.mlukov.marvels.utils.SchedulersProvider

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import android.arch.persistence.room.Room
import com.mlukov.marvels.domain.providers.ILogger
import com.mlukov.marvels.domain.providers.Logger


@Module
class ApplicationModule {

    @Provides
    @Singleton
    @AppContext
    internal fun providesAppContext(marvelsApp : MarvelsApp) : Context {

        return marvelsApp
    }

    @Provides
    @Singleton
    internal fun providesGson() : Gson {
        return GsonBuilder()
                .setDateFormat("dd/mm/yyyy HH:mm")
                .create()
    }

    @Provides
    @Singleton
    internal fun providesMarvelsDatabase( @AppContext context: Context ) :MarvelsDatabase{

        return Room.databaseBuilder( context,
                                     MarvelsDatabase::class.java,
                                     "marvels_db" )
                .build()
    }

    @Provides
    internal fun providesLocalStorageRepository(database :MarvelsDatabase): IComicsLocalRepository {

        return ComicsLocalRepository( database)
    }

    @Provides
    internal fun providesSchedulersProvider(schedulersProvider: SchedulersProvider): ISchedulersProvider {

        return schedulersProvider
    }

    @Provides
    internal fun providesMarvelsApiRepository( comicApiRepository: MarvelsRemoteRepository): IMarvelsRemoteRepository {

        return comicApiRepository
    }

    @Provides
    @Singleton
    internal fun providesIHashCalculator() :IHashCalculator{

        return HashCalculator()
    }

    @Provides
    @Singleton
    internal fun providesITimestampProvider() :ITimestampProvider{

        return TimestampProvider()
    }

    @Provides
    fun providesComicInteractor(comicInteractor: ComicInteractor): IComicInteractor {

        return comicInteractor
    }

    @Provides
    @Singleton
    fun providesLogger():ILogger{

        return Logger()
    }

}
