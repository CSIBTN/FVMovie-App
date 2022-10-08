package com.csibtn.fvmovies.api

import com.squareup.moshi.JsonClass
private const val baseImgPath: String = "https://image.tmdb.org/t/p/w500"

@JsonClass(generateAdapter = true)
data class Contributor(
    val name : String,
    val profile_path : String?
){
    val contributorPicture : String
    get() = "$baseImgPath$profile_path"
}