package com.csibtn.fvmovies.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieCast(
    val cast: List<Contributor>
)