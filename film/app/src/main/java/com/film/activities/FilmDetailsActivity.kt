package com.film.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.film.adapter.AdapterActor
import com.film.databinding.FilmDetailsBinding
import com.film.utils.FilmApplicationGlobal

class FilmDetailsActivity : AppCompatActivity() {

    private val TAG: String = FilmDetailsActivity::class.java.simpleName

    lateinit var filmDetailsBinding: FilmDetailsBinding
    lateinit var filmApplicationGlobal: FilmApplicationGlobal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filmDetailsBinding = FilmDetailsBinding.inflate(layoutInflater)
        setContentView(filmDetailsBinding.root)
        filmApplicationGlobal = applicationContext as FilmApplicationGlobal

        if (filmApplicationGlobal.resultDetailsFilm != null && filmApplicationGlobal!!.resultActorList != null) {
            filmDetailsBinding.actorsRecyclerView.layoutManager = LinearLayoutManager(this)

            println("size listGenres               = ${filmApplicationGlobal!!.resultDetailsFilm!!.listGenres!!.size}")
             println("size listProductionCompanies  = ${filmApplicationGlobal!!.resultDetailsFilm!!.listProductionCompanies!!.size}")
             println("size listProductionCountries  = ${filmApplicationGlobal!!.resultDetailsFilm!!.listProductionCountries!!.size}")
             println("size listSpokenLanguages      = ${filmApplicationGlobal!!.resultDetailsFilm!!.listSpokenLanguages!!.size}")
             println("size listCast                 = ${filmApplicationGlobal!!.resultActorList!!.listCast!!.size}")
             println("size listCrew                 = ${filmApplicationGlobal!!.resultActorList!!.listCrew!!.size}")

            filmDetailsBinding.actorsRecyclerView.adapter = AdapterActor(filmApplicationGlobal!!.resultActorList!!.listCast!!)

            val url = FilmApplicationGlobal.GET_PICTURE_URL + filmApplicationGlobal!!.resultDetailsFilm!!.backdropPath
            Glide.with(this).load(url).into(filmDetailsBinding.filmDetailImagine)
            filmDetailsBinding.filmTitolo.text = FilmApplicationGlobal.fixTooLongTitle(filmApplicationGlobal.resultDetailsFilm!!.title.toString())
            filmDetailsBinding.filmDataValue.text = filmApplicationGlobal.resultDetailsFilm!!.releaseDate.toString()
            filmDetailsBinding.filmImdbIdValue.text = filmApplicationGlobal.resultDetailsFilm!!.imdbId.toString()
            filmDetailsBinding.filmLinkHomePageValue.text = filmApplicationGlobal.resultDetailsFilm!!.homepage.toString()
        }



    }


}