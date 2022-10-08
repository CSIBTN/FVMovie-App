package com.csibtn.fvmovies.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrailerUrl(
    @Json(name = "results") val trailer : List<Trailer>
)