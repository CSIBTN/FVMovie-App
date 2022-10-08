package com.csibtn.fvmovies.model

import com.csibtn.fvmovies.api.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class MoviesRepository {
    private val movieAPI: MtdbAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        movieAPI = retrofit.create()
    }

    suspend fun findMovieOnQuery(query: String): List<Movie> {
        return movieAPI.findMovieOnQuery(query).movieItems
    }

    suspend fun fetchMovieCast(movieId: Int): List<Contributor> {
        return movieAPI.fetchCast(movieId).cast
    }

    suspend fun fetchPopularMovies(): List<Movie> {
        return movieAPI.fetchPopularMovies().movieItems
    }

    suspend fun fetchTrailerUrl(movieId: Int): Trailer {
        val trailer: Trailer = try {
            movieAPI.fetchVideoUrl(movieId).trailer[0]
        } catch (e: Exception) {
            Trailer("error")
        }
        return trailer
    }
}