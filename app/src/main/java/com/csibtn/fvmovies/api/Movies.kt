package com.csibtn.fvmovies.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movies(
    @Json(name = "results") val movieItems: List<Movie>
)



