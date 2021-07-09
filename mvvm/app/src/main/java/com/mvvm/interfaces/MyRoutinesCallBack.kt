package com.mvvm.interfaces

import androidx.lifecycle.MutableLiveData
import com.mvvm.model.Film

interface MyRoutinesCallBack {

    suspend fun loadLiveData(liveData: MutableLiveData<List<Film>>?)

}