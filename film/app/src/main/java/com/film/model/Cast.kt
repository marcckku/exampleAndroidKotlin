package com.film.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Cast (
    @set:JsonProperty("adult") var adult : Boolean?=null,
    @set:JsonProperty("gender") var gender : Int?=null,
    @set:JsonProperty("id") var id : Int?=null,
    @set:JsonProperty("known_for_department") var knownForDepartment : String?=null,
    @set:JsonProperty("name") var name : String?=null,
    @set:JsonProperty("original_name") var originalName : String?=null,
    @set:JsonProperty("popularity") var popularity : Double?=null,
    @set:JsonProperty("profile_path") var profilePath : String?=null,
    @set:JsonProperty("cast_id") var castId : Int?=null,
    @set:JsonProperty("character") var character : String?=null,
    @set:JsonProperty("credit_id") var creditId : String?=null,
    @set:JsonProperty("order") var order : Int?=null
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (adult!!) 1 else 0)
        parcel.writeInt(gender!!)
        parcel.writeInt(id!!)
        parcel.writeString(knownForDepartment)
        parcel.writeString(name)
        parcel.writeString(originalName)
        parcel.writeDouble(popularity!!)
        parcel.writeString(profilePath)
        parcel.writeInt(castId!!)
        parcel.writeString(character)
        parcel.writeString(creditId)
        parcel.writeInt(order!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cast> {
        override fun createFromParcel(parcel: Parcel): Cast {
            return Cast(parcel)
        }

        override fun newArray(size: Int): Array<Cast?> {
            return arrayOfNulls(size)
        }
    }


}