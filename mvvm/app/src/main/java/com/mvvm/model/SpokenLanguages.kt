package com.mvvm.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable


@JsonIgnoreProperties(ignoreUnknown = true)
data class SpokenLanguages(

    @set:JsonProperty("english_name")
    var englishName: String? = null,

    @set:JsonProperty("iso_639_1")
    var iso6391: String? = null,

    @set:JsonProperty("name")
    var name: String? = null

) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(englishName)
        parcel.writeString(iso6391)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SpokenLanguages> {
        override fun createFromParcel(parcel: Parcel): SpokenLanguages {
            return SpokenLanguages(parcel)
        }

        override fun newArray(size: Int): Array<SpokenLanguages?> {
            return arrayOfNulls(size)
        }
    }

}