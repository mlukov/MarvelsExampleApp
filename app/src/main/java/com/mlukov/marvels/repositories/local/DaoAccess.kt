package com.mlukov.marvels.repositories.local

import android.arch.persistence.room.*
import android.telecom.Call
import com.mlukov.marvels.repositories.local.model.LocalComics
import io.reactivex.Single

@Dao
public interface DaoAccess {

    @Insert
    fun insertComic(comics: LocalComics )

    @Query("SELECT * FROM LocalComics WHERE id = :comicId")
    fun getComicByIdAsync( comicId: Long ) : Single<LocalComics>

    @Query("SELECT * FROM LocalComics WHERE id = :comicId")
    fun getComicById( comicId: Long ) : LocalComics

    @Query("SELECT * FROM LocalComics ORDER BY id desc")
    fun getAllComics() : Single<List<LocalComics>>

    @Update
    fun updateComic( comics: LocalComics )

    @Delete
    fun deleteComic( comics: LocalComics )
}