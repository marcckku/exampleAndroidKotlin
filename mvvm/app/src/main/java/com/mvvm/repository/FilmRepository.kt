package com.mvvm.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mvvm.model.Film
import com.mvvm.model.ResultListFilm
import com.mvvm.utils.FilmApplicationGlobal
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


class FilmRepository {

    private val TAG: String = FilmRepository::class.java.simpleName
    val client = FilmApplicationGlobal.initRetrofitLazy
    private val context: CoroutineContext = Dispatchers.Main
    private val dispatchers: CoroutineContext = Dispatchers.IO
    var coroutineScope: CoroutineScope = CoroutineScope(dispatchers + SupervisorJob())

    ///Questa versione è sincrona (custom), perche ritorno il dato, ma utilizzando le coroutine + async e await
    fun getFilmsSincrono(filmApplicationGlobal: FilmApplicationGlobal): MutableLiveData<List<Film>>
    {
        var liveData = MutableLiveData<List<Film>>()
        coroutineScope.launch {
            val call = async {
                if (filmApplicationGlobal.retrofitApiFilmService != null) {
                    val call: Call<ResultListFilm> =
                        filmApplicationGlobal.retrofitApiFilmService!!.getFilmsAsyncV1()
                    call.enqueue(object : Callback<ResultListFilm> {
                        override fun onResponse(
                            call: Call<ResultListFilm>,
                            response: Response<ResultListFilm>
                        ) {
                            if (response.isSuccessful) {
                                println("Success!!")
                                Log.e(TAG, "SUCCESS!!")
                                liveData.value = (response.body() as ResultListFilm).films
                            } else {
                                println("Errore getFilms() call retrofit")
                                Log.e(TAG, "NUMBER ERROR")
                            }
                        }

                        override fun onFailure(call: Call<ResultListFilm>, t: Throwable) {
                            Log.e(TAG, "onFailure")
                            t.printStackTrace()
                        }
                    })
                }
            }
            call.await()
        }
        return liveData;
    }

    //Vecchio style, versionei <= Retrofit 2.6.0 - Do an asynchronous operation to fetch films.
    suspend fun loadFilmsAsyncV1(filmApplicationGlobal: FilmApplicationGlobal, liveData: MutableLiveData<List<Film>>)
    {
        if (filmApplicationGlobal.retrofitApiFilmService != null) {

            val call: Call<ResultListFilm> =
                filmApplicationGlobal.retrofitApiFilmService!!.getFilmsAsyncV1()
            call.enqueue(object : Callback<ResultListFilm> {
                override fun onResponse(
                    call: Call<ResultListFilm>,
                    response: Response<ResultListFilm>
                ) {
                    if (response.isSuccessful) {
                        println("Success!!")
                        Log.e(TAG, "SUCCESS!!")
                        liveData.value = (response.body() as ResultListFilm).films
                    } else {
                        println("Errore getFilms() call retrofit")
                        Log.e(TAG, "NUMBER ERROR")
                    }
                }

                override fun onFailure(call: Call<ResultListFilm>, t: Throwable) {
                    Log.e(TAG, "onFailure")
                    t.printStackTrace()
                }
            })
        }
    }

    ///////////////////////////////////Nuovo style, versioni > Retrofit 2.6.0 //////////////////////////////
    // sempre asyncrona ma customizzata da retrofit diventando syncrona.
    suspend fun loadFilmsSyncV2() = client.value.getFilmsSyncV2()

    suspend fun getDettaglioFilm(movie_id: String)= client.value.getDettaglioFilm(movie_id!!)

    suspend fun getActorsList(movie_id: String)= client.value.getActorsList(movie_id!!)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

}


