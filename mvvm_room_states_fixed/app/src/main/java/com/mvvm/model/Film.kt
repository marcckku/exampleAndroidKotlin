package com.mvvm.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@Entity(tableName = "film")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Film(

    @ColumnInfo(name = "adult")
    @set:JsonProperty("adult")
    var adult: Boolean? = null,

    @ColumnInfo(name = "backdropPath")
    @set:JsonProperty("backdrop_path")
    var backdropPath: String? = null,

    @ColumnInfo(name = "genreIds")
    @set:JsonProperty("genre_ids")
    var genreIds: List<Int>? = null,

    @PrimaryKey
    @ColumnInfo(name = "id")
    @set:JsonProperty("id")
    var id: Int? = null,

    @ColumnInfo(name = "originalLanguage")
    @set:JsonProperty("original_language")
    var originalLanguage: String? = null,

    @ColumnInfo(name = "originalTitle")
    @set:JsonProperty("original_title")
    var originalTitle: String? = null,

    @ColumnInfo(name = "overview")
    @set:JsonProperty("overview")
    var overview: String? = null,

    @ColumnInfo(name = "popularity")
    @set:JsonProperty("popularity")
    var popularity: Double? = null,

    @ColumnInfo(name = "posterPath")
    @set:JsonProperty("poster_path")
    var posterPath: String? = null,

    @ColumnInfo(name = "releaseDate")
    @set:JsonProperty("release_date")
    var releaseDate: String? = null,

    @ColumnInfo(name = "title")
    @set:JsonProperty("title")
    var title: String? = null,

    @ColumnInfo(name = "video")
    @set:JsonProperty("video")
    var video: Boolean? = null,

    @ColumnInfo(name = "voteAverage")
    @set:JsonProperty("vote_average")
    var voteAverage: Double? = null,

    @ColumnInfo(name = "voteCount")
    @set:JsonProperty("vote_count")
    var voteCount: Int? = null

) : Serializable, Parcelable {
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
    )

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