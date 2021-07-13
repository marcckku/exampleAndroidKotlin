package com.mvvm.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.adapter.AdapterFilm
import com.mvvm.databinding.ListaFilmsBinding
import com.mvvm.model.Film
import com.mvvm.utils.FilmApplicationGlobal
import com.mvvm.viewModel.ListaFilmViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * ControllerListaFilmActivity
 *
 * @AndroidEntryPoint
 * Genera un componente Hilt separato per ogni classe Android nel tuo progetto. Questi componenti
 * possono ricevere dipendenze dalle rispettive classi superiori. Spiegato qui nella gerarchia
 * dei componenti: https://developer.android.com/training/dependency-injection/hilt-android#component-hierarchy
 *
 * */

@AndroidEntryPoint
class ControllerListaFilmActivity : AppCompatActivity() {

    private val TAG: String = ControllerListaFilmActivity::class.java.simpleName

    lateinit var listaFilmsBinding: ListaFilmsBinding

    var films: MutableList<Film> = ArrayList()

    lateinit var filmApplicationGlobal: FilmApplicationGlobal

    lateinit var adapterFilm: AdapterFilm

    val viewModel: ListaFilmViewModel by viewModels() // in kotlin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listaFilmsBinding = ListaFilmsBinding.inflate(layoutInflater)
        setContentView(listaFilmsBinding.root)
        filmApplicationGlobal = applicationContext as FilmApplicationGlobal
        listaFilmsBinding.filmRecyclerView.layoutManager = LinearLayoutManager(this)

        // update UI
        viewModel.getFilmsSincrono()!!.observe(this, {
            Log.e(TAG," FILMS films.isEmpty() ===>>>  ${it.isNullOrEmpty()}")
            if (!it.isNullOrEmpty()) {
                films.addAll(it)
                adapterFilm = AdapterFilm(films)
                listaFilmsBinding.filmRecyclerView.adapter = adapterFilm
            }
        })
    }
}
