package com.mvvm.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mvvm.adapter.AdapterActor
import com.mvvm.databinding.FilmDetailsBinding
import com.mvvm.model.ResultActorList
import com.mvvm.model.ResultDetailsFilm
import com.mvvm.utils.FilmApplicationGlobal
import com.mvvm.viewModel.DetailsFilmViewModel


class ControllerFilmDetailsActivity : AppCompatActivity() {

    private val TAG: String = ControllerFilmDetailsActivity::class.java.simpleName
    lateinit var filmDetailsBinding: FilmDetailsBinding
    lateinit var filmApplicationGlobal: FilmApplicationGlobal
    lateinit var resultDetailsFilm: ResultDetailsFilm
    lateinit var resultActorList: ResultActorList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filmDetailsBinding = FilmDetailsBinding.inflate(layoutInflater)
        setContentView(filmDetailsBinding.root)

        filmApplicationGlobal = applicationContext as FilmApplicationGlobal
        val viewModel: DetailsFilmViewModel =
            ViewModelProvider(this).get(DetailsFilmViewModel::class.java)

        var movie_id = intent.getStringExtra("movie_id")

        if(!movie_id.isNullOrEmpty()){
            viewModel.dettaglioFilm(movie_id!!)?.observe(this, {
                resultDetailsFilm = it
                viewModel.getActorsList(movie_id)?.observe(this, {
                    resultActorList = it
                    if (resultDetailsFilm != null && resultActorList != null) {

                        filmDetailsBinding.actorsRecyclerView.layoutManager = LinearLayoutManager(this)

                        println("size listGenres               = ${resultDetailsFilm!!.listGenres!!.size}")
                        println("size listProductionCompanies  = ${resultDetailsFilm!!.listProductionCompanies!!.size}")
                        println("size listProductionCountries  = ${resultDetailsFilm!!.listProductionCountries!!.size}")
                        println("size listSpokenLanguages      = ${resultDetailsFilm!!.listSpokenLanguages!!.size}")
                        println("size listCast                 = ${resultActorList!!.listCast!!.size}")
                        println("size listCrew                 = ${resultActorList!!.listCrew!!.size}")

                        filmDetailsBinding.actorsRecyclerView.adapter = AdapterActor(resultActorList!!.listCast!!)

                        val url =FilmApplicationGlobal.GET_PICTURE_URL + resultDetailsFilm!!.backdropPath
                        Glide.with(this).load(url).into(filmDetailsBinding.filmDetailImagine)
                        filmDetailsBinding.filmTitolo.text              = FilmApplicationGlobal.fixTooLongTitle(resultDetailsFilm!!.title.toString())
                        filmDetailsBinding.filmDataValue.text           = resultDetailsFilm!!.releaseDate.toString()
                        filmDetailsBinding.filmImdbIdValue.text         = resultDetailsFilm!!.imdbId.toString()
                        filmDetailsBinding.filmLinkHomePageValue.text    =resultDetailsFilm!!.homepage.toString()

                        if (resultDetailsFilm?.listProductionCompanies.isNullOrEmpty() || resultDetailsFilm?.listProductionCountries.isNullOrEmpty()) {
                            filmDetailsBinding.filmCountryValue.text = ""
                            filmDetailsBinding.filmFromValue.text = ""
                        } else {
                            filmDetailsBinding.filmCountryValue.text = resultDetailsFilm?.listProductionCompanies!![0].originCountry
                            filmDetailsBinding.filmFromValue.text =
                                resultDetailsFilm?.listProductionCompanies!![0].name
                        }
                        if (resultDetailsFilm?.listSpokenLanguages.isNullOrEmpty()) {
                            filmDetailsBinding.filmLinguaValue.text = ""
                            filmDetailsBinding.filmOverviewValue.text = ""
                        } else {
                            filmDetailsBinding.filmLinguaValue.text =
                                resultDetailsFilm?.listSpokenLanguages!![0].englishName
                        }
                        if (resultDetailsFilm?.overview.isNullOrEmpty()) {
                            filmDetailsBinding.filmOverviewValue.text = ""
                        } else {
                            filmDetailsBinding.filmOverviewValue.text =
                                resultDetailsFilm?.overview
                        }
                    }
                })
            })
        }else{
            println("Nessun ID passato!! ")
        }
    }

}