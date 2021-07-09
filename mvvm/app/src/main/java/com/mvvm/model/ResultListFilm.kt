package com.mvvm.model


import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResultListFilm(

    @set:JsonProperty("page")
    var page: Int? = null,

    @set:JsonProperty("results")
    var films: List<Film>? = null,

    @set:JsonProperty("total_pages")
    var totalPages: Int? = null,

    @set:JsonProperty("total_results")
    var totalResults: Int? = null,

    ) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createTypedArrayList(Film),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(page)
        parcel.writeTypedList(films)
        parcel.writeValue(totalPages)
        parcel.writeValue(totalResults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultListFilm> {
        override fun createFromParcel(parcel: Parcel): ResultListFilm {
            return ResultListFilm(parcel)
        }

        override fun newArray(size: Int): Array<ResultListFilm?> {
            return arrayOfNulls(size)
        }
    }

}