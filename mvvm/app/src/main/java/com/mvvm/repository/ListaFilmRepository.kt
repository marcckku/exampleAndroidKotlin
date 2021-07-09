package com.mvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm.model.Film
import com.mvvm.model.ResultListFilm
import com.mvvm.utils.FilmApplicationGlobal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaFilmRepository {

    private val TAG: String = ListaFilmRepository::class.java.simpleName
    val client = FilmApplicationGlobal.initRetrofitLazy

    //Vecchio style, versionei <= Retrofit 2.6.0 - Do an asynchronous operation to fetch films.
    suspend fun loadFilmsAsync(filmApplicationGlobal: FilmApplicationGlobal, liveData: MutableLiveData<List<Film>>)
    {
        if(filmApplicationGlobal.retrofitApiFilmService != null){
            val call: Call<ResultListFilm> = filmApplicationGlobal.retrofitApiFilmService!!.getFilmsAsync()
            call.enqueue(object : Callback<ResultListFilm>{
                override fun onResponse(call: Call<ResultListFilm>, response: Response<ResultListFilm>) {
                    if (response.isSuccessful) {
                        println("Success!!")
                        Log.e(TAG, "SUCCESS!!")
                        liveData.value  = (response.body() as ResultListFilm).films
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

    //Nuovo style, versioni > Retrofit 2.6.0  - Do an asynchronous operation to fetch films.
    suspend fun loadFilmsSync() = client.value.getFilmsSync()

}


