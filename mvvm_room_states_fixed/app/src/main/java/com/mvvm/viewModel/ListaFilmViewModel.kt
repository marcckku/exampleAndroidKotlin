package com.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.model.Film
import com.mvvm.repository.DbManager
import com.mvvm.serviceImpl.ApiFilmServiceImpl
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
    val apiFilmServiceImpl: ApiFilmServiceImpl,
    val dbManager: DbManager,
    // val stateView: SavedStateHandle,
) : ViewModel() {

    private val TAG: String = ListaFilmViewModel::class.java.simpleName
    var liveData: MutableLiveData<List<Film>>? = null
    private val context: CoroutineContext = Dispatchers.Main
    private val dispatchers: CoroutineContext = Dispatchers.IO
    var coroutineScope: CoroutineScope = CoroutineScope(dispatchers + SupervisorJob())
    var films: List<Film>? = null

    init {
        Log.e(TAG, "INJECTION VALUE dbInMemory != null  ${dbManager != null}")
        /// dbInMemory.deleteAllRowsAllTables()
    }


    companion object {
        const val LIST_FILM_KEY_NAME = "films"
    }
//
//    // Expose an immutable LiveData
//    fun getName(): LiveData<String?>? {
//        return stateView.getLiveData(LIST_FILM_KEY_NAME)
//    }
//
//    fun saveNewName(newName: String?) {
//        stateView.set(LIST_FILM_KEY_NAME, newName)
//    }

    //2) ultima versione retrofit
    fun getFilmsV2(): LiveData<List<Film>>? {
        Log.e(TAG, "apiFilmServiceImpl != null  ${apiFilmServiceImpl != null}")
        Log.e(TAG, "films != null  ${films != null}")
        if (apiFilmServiceImpl != null) {
            if (liveData == null) {
                liveData = MutableLiveData<List<Film>>()
                coroutineScope.launch {
                    films = apiFilmServiceImpl.loadFilmsSyncV2().films
                    liveData!!.postValue(films)
                    films!!.forEach {
                        dbManager.insertFilm(it)
                    }
                    println("dbInMemory.getAllFilms()!!.size  ==  ${dbManager.getAllFilms()!!.size}")
                    films = null
                }
            } else if (liveData!!.value == null) {
                coroutineScope.launch {
                    films = dbManager.getAllFilms()
                    liveData!!.value = films
                    println("films!!.size  ==  ${films!!.size}")
                }
            }
        }
        return liveData
    }


    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }


}