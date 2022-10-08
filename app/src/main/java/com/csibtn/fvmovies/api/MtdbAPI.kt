package com.csibtn.fvmovies.api

import com.csibtn.fvmovies.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = BuildConfig.API_KEY

interface MtdbAPI {
    @GET(
        "search/movie" +
                "?api_key=${API_KEY}"

    )
    suspend fun findMovieOnQuery(@Query("query") query: String): Movies

    @GET(
        "movie/popular" +
                "?api_key=${API_KEY}"

    )
    suspend fun fetchPopularMovies(): Movies

    @GET(
        "movie/{movieId}/credits" +
                "?api_key=${API_KEY}"
    )
    suspend fun fetchCast(@Path("movieId") movieId: Int): MovieCast

    @GET(
        "movie/{movieId}/videos" +
                "?api_key=${API_KEY}"
    )
    suspend fun fetchVideoUrl(@Path("movieId") movieId: Int): TrailerUrl
}