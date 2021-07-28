package com.mvvm.converter

import androidx.room.TypeConverter
import com.google.gson.Gson


class Converters {

    @TypeConverter
    fun listToJson(value: List<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()


//    @TypeConverter
//    fun fromString(value: String?): ArrayList<String?>? {
//        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
//        return Gson().fromJson(value, listType)
//    }

//    @TypeConverter
//    fun fromList(list: List<Int>?): String? {
//        val gson = Gson()
//        return gson.toJson(list)
//    }
}
