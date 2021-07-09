package com.mvvm.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.adapter.AdapterFilm
import com.mvvm.databinding.ListaFilmsBinding
import com.mvvm.model.Film
import com.mvvm.utils.FilmApplicationGlobal
import com.mvvm.viewModel.ListaFilmViewModel


class ControllerListaFilmActivity : AppCompatActivity() {

    private val TAG: String = ControllerListaFilmActivity::class.java.simpleName
    lateinit var listaFilmsBinding: ListaFilmsBinding
    var films: MutableList<Film> = ArrayList()
    lateinit var filmApplicationGlobal: FilmApplicationGlobal
    lateinit var adapterFilm: AdapterFilm

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listaFilmsBinding = ListaFilmsBinding.inflate(layoutInflater)
        setContentView(listaFilmsBinding.root)
        filmApplicationGlobal = applicationContext as FilmApplicationGlobal
        listaFilmsBinding.filmRecyclerView.layoutManager = LinearLayoutManager(this)
        val viewModel: ListaFilmViewModel =
            ViewModelProvider(this).get(ListaFilmViewModel::class.java)

//        viewModel.getFilmsV1(filmApplicationGlobal)?.observe(this, {
//            // update UI
//            films.addAll(it)
//            println(" FILMS films.isEmpty() ===>>>  ${films.isEmpty()}")
//            if (!films.isNullOrEmpty()) {
//                adapterFilm = AdapterFilm(films, filmApplicationGlobal)
//                listaFilmsBinding.filmRecyclerView.adapter = adapterFilm
//            }
//        })

        viewModel.getFilmsV2()?.observe(this, {
            // update UI
            films.addAll(it)
            println(" FILMS films.isEmpty() ===>>>  ${films.isEmpty()}")
            if (!films.isNullOrEmpty()) {
                adapterFilm = AdapterFilm(films, filmApplicationGlobal)
                listaFilmsBinding.filmRecyclerView.adapter = adapterFilm
            }
        })
    }
}
