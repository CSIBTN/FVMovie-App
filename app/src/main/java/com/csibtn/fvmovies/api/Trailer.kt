package com.csibtn.fvmovies.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Trailer(
    @Json(name = "key") val youtubeKey : String
)