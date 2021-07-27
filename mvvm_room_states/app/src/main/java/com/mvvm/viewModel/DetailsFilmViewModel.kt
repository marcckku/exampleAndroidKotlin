package com.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.model.ResultActorList
import com.mvvm.model.ResultDetailsFilm
import com.mvvm.serviceImpl.ApiFilmServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class DetailsFilmViewModel
@Inject constructor(
    private val apiFilmServiceImpl: ApiFilmServiceImpl,
) : ViewModel() {

    private val TAG: String = DetailsFilmViewModel::class.java.simpleName
    var details: MutableLiveData<ResultDetailsFilm>? = null
    var actors: MutableLiveData<ResultActorList>? = null
    private val context: CoroutineContext = Dispatchers.Main
    private val dispatchers: CoroutineContext = Dispatchers.IO
    var coroutineScope: CoroutineScope = CoroutineScope(dispatchers + SupervisorJob())

    fun dettaglioFilm(movie_id: String): LiveData<ResultDetailsFilm>? {
        if (details == null) {
            details = MutableLiveData<ResultDetailsFilm>()
        }
        coroutineScope.launch {
            details!!.postValue(apiFilmServiceImpl.getDettaglioFilm(movie_id))
            println("Success getDetailsList!!")
            Log.e(TAG, "SUCCESS!!")
        }
        return details
    }

    fun getActorsList(movie_id: String) : MutableLiveData<ResultActorList>?  {
        if (actors == null) {
            actors =  MutableLiveData<ResultActorList>()
        }
        coroutineScope.launch {
            actors!!.postValue(apiFilmServiceImpl.getActorsList(movie_id))
            println("Success getActorsList!!")
            Log.e(TAG, "SUCCESS!!")
        }
        return actors

    }
}