package com.mvvm.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.adapter.AdapterFilm
import com.mvvm.databinding.ListaFilmsBinding
import com.mvvm.model.Film
import com.mvvm.utils.ApplicationGlobal
import com.mvvm.viewModel.ListaFilmViewModel
import dagger.hilt.android.AndroidEntryPoint

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

    private lateinit var listaFilmsBinding: ListaFilmsBinding

    private var films: MutableList<Film> = ArrayList()

    private lateinit var applicationGlobal: ApplicationGlobal

    private lateinit var adapterFilm: AdapterFilm

    val viewModel: ListaFilmViewModel by viewModels() // in kotlin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listaFilmsBinding = ListaFilmsBinding.inflate(layoutInflater)
        setContentView(listaFilmsBinding.root)
        applicationGlobal = applicationContext as ApplicationGlobal
        listaFilmsBinding.filmRecyclerView.layoutManager = LinearLayoutManager(this)

        // update UI
        viewModel.getFilmsV2()!!.observe(this, {
            Log.e(TAG, " FILMS films.isEmpty() ===>>>  ${it.isNullOrEmpty()}")
            if (!it.isNullOrEmpty()) {
                films.addAll(it)
                adapterFilm = AdapterFilm(films)
                listaFilmsBinding.filmRecyclerView.adapter = adapterFilm
            }
        })
    }
}
