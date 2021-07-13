package com.mvvm.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProductionCountries(

    @set:JsonProperty("iso_3166_1")
    var iso31661: String? = null,

    @set:JsonProperty("name")
    var name: String? = null

) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(iso31661)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductionCountries> {
        override fun createFromParcel(parcel: Parcel): ProductionCountries {
            return ProductionCountries(parcel)
        }

        override fun newArray(size: Int): Array<ProductionCountries?> {
            return arrayOfNulls(size)
        }
    }

}