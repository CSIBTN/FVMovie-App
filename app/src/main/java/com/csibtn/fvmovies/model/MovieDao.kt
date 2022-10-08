package com.csibtn.fvmovies.model

import androidx.room.*
import com.csibtn.fvmovies.api.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Update
    suspend fun updateMovie(movie: Movie)

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    suspend fun getMovie(movieId: Int): Movie

    @Query("SELECT * FROM Movie WHERE is_favourite = 1")
    fun getFavourites(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE is_currently_watching = 1")
    fun getCurrentlyWatching(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE is_finished = 1")
    fun getFinished(): Flow<List<Movie>>

    @Insert
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)
}