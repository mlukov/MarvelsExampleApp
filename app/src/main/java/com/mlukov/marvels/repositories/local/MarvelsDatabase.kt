package com.mlukov.marvels.repositories.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.mlukov.marvels.repositories.local.model.LocalComics

@Database(entities = arrayOf( LocalComics::class ), version = 1, exportSchema = false)
abstract class MarvelsDatabase: RoomDatabase() {

    abstract fun daoAccess(): DaoAccess
}