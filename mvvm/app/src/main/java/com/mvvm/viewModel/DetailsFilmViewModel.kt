package com.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.model.Film
import com.mvvm.model.ResultActorList
import com.mvvm.model.ResultDetailsFilm
import com.mvvm.repository.FilmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailsFilmViewModel  : ViewModel() {

    private val TAG : String = DetailsFilmViewModel::class.java.simpleName
    var details : MutableLiveData<ResultDetailsFilm>? = null
    var actors : MutableLiveData<ResultActorList>? = null
    var filmRepository: FilmRepository = FilmRepository()
    private val context: CoroutineContext = Dispatchers.Main
    private val dispatchers: CoroutineContext = Dispatchers.IO
    var coroutineScope: CoroutineScope = CoroutineScope(dispatchers + SupervisorJob())


    fun dettaglioFilm(movie_id: String) : LiveData<ResultDetailsFilm>?  {
        if (details == null) {
            details =  MutableLiveData<ResultDetailsFilm>()
        }
         coroutineScope.launch {
            details!!.postValue(filmRepository.getDettaglioFilm(movie_id))
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
            actors!!.postValue(filmRepository.getActorsList(movie_id!!))
            println("Success getActorsList!!")
            Log.e(TAG, "SUCCESS!!")
        }
        return actors

    }
}