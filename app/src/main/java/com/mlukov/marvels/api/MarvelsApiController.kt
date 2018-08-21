package com.mlukov.marvels.api

import com.mlukov.marvels.api.model.ComicDataResponse
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelsApiController {

    @GET("comics ")
    fun getComics( @Query( "limits") limits: Long,
                   @Query("ts")  timeStamp:String,
                   @Query("apikey") apiKey: String ,
                   @Query("hash") hash:String ): Single<ComicDataResponse>

}
