package com.film.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Film(

    @set:JsonProperty("adult")
    var adult: Boolean? = null,


    @set:JsonProperty("backdrop_path")
    var backdropPath: String? = null,


    @set:JsonProperty("genre_ids")
    var genreIds: List<Int>? = null,


    @set:JsonProperty("id")
    var id: Int? = null,


    @set:JsonProperty("original_language")
    var originalLanguage: String? = null,


    @set:JsonProperty("original_title")
    var originalTitle: String? = null,


    @set:JsonProperty("overview")
    var overview: String? = null,


    @set:JsonProperty("popularity")
    var popularity: Double? = null,


    @set:JsonProperty("poster_path")
    var posterPath: String? = null,


    @set:JsonProperty("release_date")
    var releaseDate: String? = null,


    @set:JsonProperty("title")
    var title: String? = null,


    @set:JsonProperty("video")
    var video: Boolean? = null,


    @set:JsonProperty("vote_average")
    var voteAverage: Double? = null,


    @set:JsonProperty("vote_count")
    var voteCount: Int?=null

) : Serializable , Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        TODO("genreIds"),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(adult)
        parcel.writeString(backdropPath)
        parcel.writeValue(id)
        parcel.writeString(originalLanguage)
        parcel.writeString(originalTitle)
        parcel.writeString(overview)
        parcel.writeValue(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeString(title)
        parcel.writeValue(video)
        parcel.writeValue(voteAverage)
        parcel.writeValue(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Film> {
        override fun createFromParcel(parcel: Parcel): Film {
            return Film(parcel)
        }

        override fun newArray(size: Int): Array<Film?> {
            return arrayOfNulls(size)
        }
    }

}