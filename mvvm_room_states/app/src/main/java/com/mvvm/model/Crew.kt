package com.mvvm.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable


data class Crew(

    @set:JsonProperty("adult") var adult: Boolean? = null,
    @set:JsonProperty("gender") var gender: Int? = null,
    @set:JsonProperty("id") var id: Int? = null,
    @set:JsonProperty("known_for_department") var knownForDepartment: String? = null,
    @set:JsonProperty("name") var name: String? = null,
    @set:JsonProperty("original_name") var originalName: String? = null,
    @set:JsonProperty("popularity") var popularity: Double? = null,
    @set:JsonProperty("profile_path") var profilePath: String? = null,
    @set:JsonProperty("credit_id") var creditId: String? = null,
    @set:JsonProperty("department") var department: String? = null,
    @set:JsonProperty("job") var job: String? = null,

    ) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(adult)
        parcel.writeValue(gender)
        parcel.writeValue(id)
        parcel.writeString(knownForDepartment)
        parcel.writeString(name)
        parcel.writeString(originalName)
        parcel.writeValue(popularity)
        parcel.writeString(profilePath)
        parcel.writeString(creditId)
        parcel.writeString(department)
        parcel.writeString(job)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Crew> {
        override fun createFromParcel(parcel: Parcel): Crew {
            return Crew(parcel)
        }

        override fun newArray(size: Int): Array<Crew?> {
            return arrayOfNulls(size)
        }
    }

}