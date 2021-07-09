package com.mvvm.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.adapter.AdapterFilm
import com.mvvm.databinding.ListaFilmsBinding
import com.mvvm.model.Film
import com.mvvm.model.ResultListFilm
import com.mvvm.utils.FilmApplicationGlobal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaFilmActivity : AppCompatActivity() {

    private val TAG: String = ListaFilmActivity::class.java.simpleName
    lateinit var listaFilmsBinding: ListaFilmsBinding
    lateinit var filmApplicationGlobal: FilmApplicationGlobal
    lateinit var adapterFilm: AdapterFilm
    lateinit var films: List<Film>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listaFilmsBinding = ListaFilmsBinding.inflate(layoutInflater)
        setContentView(listaFilmsBinding.root)
        listaFilmsBinding.filmRecyclerView.layoutManager = LinearLayoutManager(this)
        filmApplicationGlobal = applicationContext as FilmApplicationGlobal
        getFilms()
    }


    private fun getFilms() {
        if (filmApplicationGlobal.retrofitApiFilmService != null) {
            val call: Call<ResultListFilm> =
                filmApplicationGlobal.retrofitApiFilmService!!.getFilmsAsync()
            call.enqueue(object : Callback<ResultListFilm> {
                override fun onResponse(
                    call: Call<ResultListFilm>,
                    response: Response<ResultListFilm>
                ) {
                    if (response.isSuccessful) {
                        println("Success!!")
                        Log.e(TAG, "SUCCESS!!")
                        var results = response.body() as ResultListFilm
                        films = results.films as List<Film>
                        adapterFilm = AdapterFilm(films, filmApplicationGlobal)
                        listaFilmsBinding.filmRecyclerView.adapter = adapterFilm
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

}