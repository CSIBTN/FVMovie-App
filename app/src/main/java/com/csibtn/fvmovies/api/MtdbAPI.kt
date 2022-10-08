package com.csibtn.fvmovies.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "d3e83dc1ae7c5e4f64499442c1fd723c"

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