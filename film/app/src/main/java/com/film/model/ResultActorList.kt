package com.film.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class ResultActorList (
    @set:JsonProperty("id") var id : Int?=null,
    @set:JsonProperty("cast") var listCast : List<Cast>?=null,
    @set:JsonProperty("crew") var listCrew : List<Crew>?=null,
): Serializable, Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createTypedArrayList(Cast),
        parcel.createTypedArrayList(Crew)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeTypedList(listCast)
        parcel.writeTypedList(listCrew)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultActorList> {
        override fun createFromParcel(parcel: Parcel): ResultActorList {
            return ResultActorList(parcel)
        }

        override fun newArray(size: Int): Array<ResultActorList?> {
            return arrayOfNulls(size)
        }
    }

}