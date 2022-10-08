package com.csibtn.fvmovies.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.csibtn.fvmovies.api.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase(){
abstract fun crimeDao() : MovieDao
}