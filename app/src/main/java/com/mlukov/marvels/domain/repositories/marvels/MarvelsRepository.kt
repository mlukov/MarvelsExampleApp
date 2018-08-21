package com.mlukov.marvels.domain.repositories.marvels


import android.util.Log
import com.mlukov.marvels.BuildConfig
import com.mlukov.marvels.api.MarvelsApiController
import com.mlukov.marvels.api.model.ArticleDetailApiData
import com.mlukov.marvels.api.model.ArticleListApi
import com.mlukov.marvels.api.model.Comic
import com.mlukov.marvels.api.model.ComicDataResponse
import com.mlukov.marvels.domain.calculators.IHashCalculator
import com.mlukov.marvels.domain.providers.ITimestampProvider
import com.mlukov.marvels.utils.SchedulersProvider
import io.reactivex.Observable

import javax.inject.Inject
import io.reactivex.Single
import io.reactivex.functions.Function


class MarvelsRepository
@Inject
constructor(private var apiController : MarvelsApiController,
            private var hashCalculator: IHashCalculator,
            private val timestampProvider: ITimestampProvider,
            private val schedulersProvider: SchedulersProvider) : IMarvelsRepository {


    override fun getArticleList( ) : Single<ArticleListApi> {

        val limit =100L
        val timeStamp = timestampProvider.getTimeStamp()


        apiController.getComics( limit, timeStamp,
                BuildConfig.PUBLIC_KEY,
                calculateHash(timeStamp, BuildConfig.PRIVATE_KEY, BuildConfig.PUBLIC_KEY))
                .map(Function<ComicDataResponse, List<Comic>> {
                    comicDataResponse -> comicDataResponse?.data?.results ?: listOf()
                })
                .subscribeOn( schedulersProvider.ioScheduler())
                .observeOn( schedulersProvider.uiScheduler())
                .subscribe({
                    for (  comic in it ){
                        Log.i( "T", comic.title )
                    }
                },{
                    Log.d( "TAG", it.message, it)
                })

        return Single.just( ArticleListApi() )// apiController.getArticleList()
    }

    override fun getArticleDetails(articleId : Int) : Single<ArticleDetailApiData> {

        return Single.just( ArticleDetailApiData() )//apiController.getArticleDetails(articleId)
    }

    private fun calculateHash(timeStamp: String, privateKey: String, publicKey: String): String {

        return hashCalculator.calculate(timeStamp, privateKey, publicKey)
    }
}