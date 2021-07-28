package com.mvvm.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mvvm.adapter.AdapterActor
import com.mvvm.databinding.FilmDetailsBinding
import com.mvvm.model.ResultActorList
import com.mvvm.model.ResultDetailsFilm
import com.mvvm.utils.ApplicationGlobal
import com.mvvm.viewModel.DetailsFilmViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ControllerFilmDetailsActivity : AppCompatActivity() {

    private val TAG: String = ControllerFilmDetailsActivity::class.java.simpleName

    lateinit var filmDetailsBinding: FilmDetailsBinding

    lateinit var applicationGlobal: ApplicationGlobal

    lateinit var resultDetailsFilm: ResultDetailsFilm

    lateinit var resultActorList: ResultActorList

    val viewModel: DetailsFilmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filmDetailsBinding = FilmDetailsBinding.inflate(layoutInflater)
        setContentView(filmDetailsBinding.root)
        applicationGlobal = applicationContext as ApplicationGlobal

        var movie_id = intent.getStringExtra("movie_id")

        if (!movie_id.isNullOrEmpty()) {
            viewModel.dettaglioFilm(movie_id)?.observe(this, { it ->
                resultDetailsFilm = it
                if (resultDetailsFilm != null) {
                    viewModel.getActorsList(movie_id)?.observe(this, { it ->
                        resultActorList = it
                        if (resultActorList != null) {
                            filmDetailsBinding.actorsRecyclerView.layoutManager =
                                LinearLayoutManager(this)
                            Log.e(
                                TAG,
                                "size listGenres               = ${resultDetailsFilm.listGenres!!.size}"
                            )
                            Log.e(
                                TAG,
                                "size listProductionCompanies  = ${resultDetailsFilm.listProductionCompanies!!.size}"
                            )
                            Log.e(
                                TAG,
                                "size listProductionCountries  = ${resultDetailsFilm.listProductionCountries!!.size}"
                            )
                            Log.e(
                                TAG,
                                "size listSpokenLanguages      = ${resultDetailsFilm.listSpokenLanguages!!.size}"
                            )
                            Log.e(
                                TAG,
                                "size listCast                 = ${resultActorList.listCast!!.size}"
                            )
                            Log.e(
                                TAG,
                                "size listCrew                 = ${resultActorList.listCrew!!.size}"
                            )

                            filmDetailsBinding.actorsRecyclerView.adapter =
                                AdapterActor(resultActorList.listCast!!)

                            val url =
                                ApplicationGlobal.GET_PICTURE_URL + resultDetailsFilm.backdropPath
                            Glide.with(this).load(url).into(filmDetailsBinding.filmDetailImagine)
                            filmDetailsBinding.filmTitolo.text =
                                ApplicationGlobal.fixTooLongTitle(resultDetailsFilm.title)
                            filmDetailsBinding.filmDataValue.text =
                                resultDetailsFilm.releaseDate
                            filmDetailsBinding.filmImdbIdValue.text =
                                resultDetailsFilm.imdbId
                            filmDetailsBinding.filmLinkHomePageValue.text =
                                resultDetailsFilm.homepage

                            if (resultDetailsFilm.listProductionCompanies.isNullOrEmpty() || resultDetailsFilm.listProductionCountries.isNullOrEmpty()) {
                                filmDetailsBinding.filmCountryValue.text = ""
                                filmDetailsBinding.filmFromValue.text = ""
                            } else {
                                filmDetailsBinding.filmCountryValue.text =
                                    resultDetailsFilm.listProductionCompanies!![0].originCountry
                                filmDetailsBinding.filmFromValue.text =
                                    resultDetailsFilm.listProductionCompanies!![0].name
                            }
                            if (resultDetailsFilm.listSpokenLanguages.isNullOrEmpty()) {
                                filmDetailsBinding.filmLinguaValue.text = ""
                                filmDetailsBinding.filmOverviewValue.text = ""
                            } else {
                                filmDetailsBinding.filmLinguaValue.text =
                                    resultDetailsFilm.listSpokenLanguages!![0].englishName
                            }
                            if (resultDetailsFilm.overview.isNullOrEmpty()) {
                                filmDetailsBinding.filmOverviewValue.text = ""
                            } else {
                                filmDetailsBinding.filmOverviewValue.text =
                                    resultDetailsFilm.overview
                            }
                        }
                    })
                }
            })
        } else {
            println("Nessun ID passato!! ")
        }
    }

}