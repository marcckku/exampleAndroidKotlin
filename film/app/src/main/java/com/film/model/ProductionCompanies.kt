package com.film.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProductionCompanies(

    @set:JsonProperty("id")
    var id: Int? = null,

    @set:JsonProperty("logo_path")
    var logoPath: String? = null,

    @set:JsonProperty("name")
    var name: String? = null,

    @set:JsonProperty("origin_country")
    var originCountry: String? = null,

    ): Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(logoPath)
        parcel.writeString(name)
        parcel.writeString(originCountry)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductionCompanies> {
        override fun createFromParcel(parcel: Parcel): ProductionCompanies {
            return ProductionCompanies(parcel)
        }

        override fun newArray(size: Int): Array<ProductionCompanies?> {
            return arrayOfNulls(size)
        }
    }

}