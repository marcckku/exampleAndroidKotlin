package com.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.model.Film
import com.mvvm.repository.FilmRepository
import com.mvvm.serviceImpl.ApiFilmServiceImpl
import com.mvvm.utils.FilmApplicationGlobal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 *  https://medium.com/swlh/how-can-we-use-coroutinescopes-in-kotlin-2210695f0e89
 *
 * Se l'utente lascia lo schermo prima che getFilms(...) restituisca un valore,
 * il sistema chiama onCleared e il CoroutineScope di ViewModel verrà annullato.
 * Ciò assicurerà che i valori di ogni film della lista Films non venga modificato
 * e il codice non tenterà di aggiornare uno schermo non più esistente.
 *
 * Tutte le attività sincrone e asincrone eseguite o avviate da una funzione di
 * sospensione verranno annullate quando viene annullato il CoroutineScope in
 * cui è in esecuzione la funzione di sospensione, purché siano in esecuzione
 * all'interno dello stesso CoroutineScope. Nessuna attività (asincrona) è orfana
 * e lasciata in esecuzione.
 *
 * */

/**
 *  https://medium.com/@taman.neupane/yes-this-basically-means-you-cannot-change-mutablelivedata-value-if-you-are-not-in-main-thread-2b740c892df9
 *
 * Solo se la chiamata al server, non è eseguita sul Thread Principale allora esegui "liveData!!.postValue"
 * Altrimenti esegui il codice qui sotto, solo nel Thread principale!!
 *    GlobalScope.launch {
 *       liveData!!.value =  filmRepository.loadFilmsSync().films
 *   }
 *
 * */


/**
 * Per ottenere le dipendenze da un componente, utilizzare @HiltViewModel più l'annotazione @Inject,
 * in questo caso a livello di costruttore :  class ListaFilmViewModel @Inject constructor(...)
 * */


@HiltViewModel
class ListaFilmViewModel
@Inject constructor(
        val filmApplicationGlobal: FilmApplicationGlobal,
        val filmRepository: FilmRepository, // <<--  delete this field
        val apiFilmServiceImpl : ApiFilmServiceImpl, //apiFilmServiceImpl sostituisce filmRepository
        someString: String,
    ) : ViewModel() {

    private val TAG: String = ListaFilmViewModel::class.java.simpleName
    var liveData: MutableLiveData<List<Film>>? = null
    private val context: CoroutineContext = Dispatchers.Main
    private val dispatchers: CoroutineContext = Dispatchers.IO
    var coroutineScope: CoroutineScope = CoroutineScope(dispatchers + SupervisorJob())
   // lateinit var filmRepository: FilmRepository  // <<--  delete this field

    init {
        Log.e(TAG, "INJECTION VALUE someString  $someString")
        Log.e(TAG, "INJECTION VALUE filmApplicationGlobal !=null  ${filmApplicationGlobal != null}")
        Log.e(TAG, "INJECTION VALUE filmRepository !=null  ${filmRepository != null}")
    }

    //1) vecchia versione retrofit
    fun getFilmsV1(): LiveData<List<Film>>? {
        Log.e(TAG, "apiFilmServiceImpl != null  ${apiFilmServiceImpl != null}")
        if (apiFilmServiceImpl != null) {
            if (liveData == null) {
                liveData = MutableLiveData<List<Film>>()
                coroutineScope.launch {
                    filmRepository.loadFilmsAsyncV1(liveData!!)
                }
            }
        }
        return liveData
    }


    //2) ultima versione retrofit
    fun getFilmsV2(): LiveData<List<Film>>? {
        Log.e(TAG, "apiFilmServiceImpl != null  ${apiFilmServiceImpl != null}")
        if (apiFilmServiceImpl != null) {
            if (liveData == null) {
                liveData = MutableLiveData<List<Film>>()
            }
            coroutineScope.launch {
                liveData!!.postValue(apiFilmServiceImpl.loadFilmsSyncV2().films)
            }
        }
        return liveData
    }

    //3)  customize - chiamata syncrona
    fun getFilmsSincrono(): LiveData<List<Film>>? {
        Log.e(TAG, "apiFilmServiceImpl != null  ${apiFilmServiceImpl != null}")
        if (apiFilmServiceImpl != null) {
            if (liveData == null) {
                liveData = MutableLiveData<List<Film>>()
                liveData = filmRepository.getFilmsSincrono()
            }
        }
        return liveData
    }

    override fun onCleared() {
        coroutineScope.cancel()
    }


}