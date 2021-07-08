package com.film.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.film.adapter.AdapterFilm
import com.film.databinding.ListaFilmsBinding
import com.film.model.Film
import com.film.model.ResultListFilm
import com.film.utils.FilmApplicationGlobal
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
        if (filmApplicationGlobal.retrofitService != null) {
            val call: Call<ResultListFilm> = filmApplicationGlobal.retrofitService!!.getFilms()
            call.enqueue(object : Callback<ResultListFilm> {
                override fun onResponse(call: Call<ResultListFilm>, response: Response<ResultListFilm>) {
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