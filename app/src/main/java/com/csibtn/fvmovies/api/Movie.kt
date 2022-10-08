package com.csibtn.fvmovies.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

private const val baseImgPath: String = "https://image.tmdb.org/t/p/w500"

@Entity
@JsonClass(generateAdapter = true)
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    @Json(name = "vote_average") val score: Double,
    val poster_path: String?,
) : java.io.Serializable {
    val posterUrl: String
        get() = "$baseImgPath$poster_path"

    @ColumnInfo(name = "is_favourite")
    var favourite: Boolean = false

    @ColumnInfo(name = "is_currently_watching")
    var currentlyWatching: Boolean = false

    @ColumnInfo(name = "is_finished")
    var finished: Boolean = false

}

