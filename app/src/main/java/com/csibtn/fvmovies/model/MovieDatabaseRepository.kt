package com.csibtn.fvmovies.model

import android.content.Context
import androidx.room.Room
import com.csibtn.fvmovies.api.Movie
import kotlinx.coroutines.flow.Flow

private const val DATABASE_NAME = "MOVIES"

class MovieDatabaseRepository private constructor(context: Context) {
    private val database: MovieDatabase = Room.databaseBuilder(
        context.applicationContext,
        MovieDatabase::class.java,
        DATABASE_NAME
    ).build()


    fun getFavourites(): Flow<List<Movie>> = database.crimeDao().getFavourites()

    fun getCurrentlyWatching(): Flow<List<Movie>> = database.crimeDao().getCurrentlyWatching()

    fun getFinished(): Flow<List<Movie>> = database.crimeDao().getFinished()

    suspend fun updateMovie(movie: Movie) = database.crimeDao().updateMovie(movie)

    suspend fun getMovie(movieId : Int): Movie = database.crimeDao().getMovie(movieId)

    suspend fun insertMovie(movie: Movie) {
        database.crimeDao().insertMovie(movie)
    }

    suspend fun deleteMovie(movie: Movie) {
        database.crimeDao().deleteMovie(movie)
    }

    companion object {
        private var INSTANCE: MovieDatabaseRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MovieDatabaseRepository(context)
            }
        }

        fun get(): MovieDatabaseRepository {
            return INSTANCE
                ?: throw IllegalStateException("MovieDatabaseRepository must be initialized")
        }
    }
}