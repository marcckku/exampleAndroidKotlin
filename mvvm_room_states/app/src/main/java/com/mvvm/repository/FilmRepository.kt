package com.mvvm.repository

import android.util.Log
import com.mvvm.interfaces.ApiFilmService
import com.mvvm.utils.FilmApplicationGlobal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext


class FilmRepository constructor(
    val client: Lazy<ApiFilmService>,
    val filmApplicationGlobal: FilmApplicationGlobal
) {

    private val TAG: String = FilmRepository::class.java.simpleName
    private val context: CoroutineContext = Dispatchers.Main
    private val dispatchers: CoroutineContext = Dispatchers.IO
    var coroutineScope: CoroutineScope = CoroutineScope(dispatchers + SupervisorJob())

    init {
        Log.e(TAG, "client != null ${client != null}")
        Log.e(TAG, "filmApplicationGlobal != null ${filmApplicationGlobal != null}")
    }

//    ///////////////////////////////////////////////////////////////////////////////////////////////////////

}


