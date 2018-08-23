package com.mlukov.marvels.di.modules

import android.util.Log
import com.google.gson.Gson
import com.mlukov.marvels.BuildConfig
import com.mlukov.marvels.repositories.remote.MarvelsApiController
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule() {

    companion object {
        val TAG = NetworkModule::class.java.simpleName
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient{


        return OkHttpClient.Builder()
                .connectTimeout(2L, TimeUnit.MINUTES)
                .readTimeout(2L, TimeUnit.MINUTES)
                .writeTimeout(2L, TimeUnit.MINUTES)
                .build()
    }


    @Provides
    @Singleton
    fun providesMarvelsApiController( okHttpClient : OkHttpClient, gson : Gson ): MarvelsApiController{

        val retrofit = Retrofit.Builder()
                .baseUrl( BuildConfig.API_ENDPOINT)
                .client( okHttpClient )
                .addConverterFactory( GsonConverterFactory.create( gson ) )
                .addCallAdapterFactory( RxJava2CallAdapterFactory.create() )
                .build()

        return retrofit.create(MarvelsApiController::class.java )
    }

}