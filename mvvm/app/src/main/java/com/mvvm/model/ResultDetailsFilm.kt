package com.mvvm.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * Proprieta "belongsToCollection" tolta perchè sempre nulla!!,
 * e retrofit da problemi con la deserealizazzione del json.
 *
 * */

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResultDetailsFilm(

    @set:JsonProperty("adult")
    var adult: Boolean? = null,


    @set:JsonProperty("backdrop_path")
    var backdropPath: String? = null,


//    @set:JsonProperty("belongs_to_collection")
//    var belongsToCollection : String?=null,


    @set:JsonProperty("budget")
    var budget: Int? = null,


    @set:JsonProperty("genres")
    var listGenres: List<Genres>? = null,


    @set:JsonProperty("homepage")
    var homepage: String? = null,


    @set:JsonProperty("id")
    var id: Int? = null,


    @set:JsonProperty("imdb_id")
    var imdbId: String? = null,


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


    @set:JsonProperty("production_companies")
    var listProductionCompanies: List<ProductionCompanies>? = null,


    @set:JsonProperty("production_countries")
    var listProductionCountries: List<ProductionCountries>? = null,


    @set:JsonProperty("release_date")
    var releaseDate: String? = null,


    @set:JsonProperty("revenue")
    var revenue: Int? = null,


    @set:JsonProperty("runtime")
    var runtime: Int? = null,


    @set:JsonProperty("spoken_languages")
    var listSpokenLanguages: List<SpokenLanguages>? = null,


    @set:JsonProperty("status")
    var status: String? = null,


    @set:JsonProperty("tagline")
    var tagline: String? = null,


    @set:JsonProperty("title")
    var title: String? = null,


    @set:JsonProperty("video")
    var video: Boolean? = null,


    @set:JsonProperty("vote_average")
    var voteAverage: Double? = null,


    @set:JsonProperty("vote_count")
    var voteCount: Int? = null,
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this() {
        adult = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        backdropPath = parcel.readString()
        //   belongsToCollection = parcel.readString()
        budget = parcel.readValue(Int::class.java.classLoader) as? Int
        homepage = parcel.readString()
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        imdbId = parcel.readString()
        originalLanguage = parcel.readString()
        originalTitle = parcel.readString()
        overview = parcel.readString()
        popularity = parcel.readValue(Double::class.java.classLoader) as? Double
        posterPath = parcel.readString()
        releaseDate = parcel.readString()
        revenue = parcel.readValue(Int::class.java.classLoader) as? Int
        runtime = parcel.readValue(Int::class.java.classLoader) as? Int
        status = parcel.readString()
        tagline = parcel.readString()
        title = parcel.readString()
        video = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        voteAverage = parcel.readValue(Double::class.java.classLoader) as? Double
        voteCount = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(adult)
        parcel.writeString(backdropPath)
        //  parcel.writeString(belongsToCollection ?: "" )
        parcel.writeValue(budget)
        parcel.writeString(homepage)
        parcel.writeValue(id)
        parcel.writeString(imdbId)
        parcel.writeString(originalLanguage)
        parcel.writeString(originalTitle)
        parcel.writeString(overview)
        parcel.writeValue(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeValue(revenue)
        parcel.writeValue(runtime)
        parcel.writeString(status)
        parcel.writeString(tagline)
        parcel.writeString(title)
        parcel.writeValue(video)
        parcel.writeValue(voteAverage)
        parcel.writeValue(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultDetailsFilm> {
        override fun createFromParcel(parcel: Parcel): ResultDetailsFilm {
            return ResultDetailsFilm(parcel)
        }

        override fun newArray(size: Int): Array<ResultDetailsFilm?> {
            return arrayOfNulls(size)
        }
    }

}