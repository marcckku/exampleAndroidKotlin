package com.film.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.film.activities.FilmDetailsActivity
import com.film.databinding.FilmItemRowBinding
import com.film.model.Film
import com.film.model.ResultActorList
import com.film.model.ResultDetailsFilm
import com.film.utils.FilmApplicationGlobal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterFilm(private val listaFilms: List<Film>, filmApplicationGlobal: FilmApplicationGlobal)
    : RecyclerView.Adapter<AdapterFilm.FilmViewHolder?>() {
    private val TAG: String = AdapterFilm::class.java.simpleName
    var filmApplicationGlobal: FilmApplicationGlobal?=null

    init {
        this.filmApplicationGlobal = filmApplicationGlobal
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(viewGroup.context)
        var view = FilmItemRowBinding.inflate(inflater, viewGroup, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(listaFilms[position])
    }

    override fun getItemCount(): Int {
        return listaFilms.size
    }

    inner class FilmViewHolder(val filmItemRowBinding: FilmItemRowBinding) : RecyclerView.ViewHolder(filmItemRowBinding.root){
        fun bind(film: Film){
            with(itemView){
                //reset values
                filmItemRowBinding.filmImagine.setImageBitmap(null)
                filmItemRowBinding.filmImagine.setImageDrawable(null)
                filmItemRowBinding.filmTitolo.text       =""
                //  filmItemRowBinding.filmSommario.text =""
                filmItemRowBinding.filmDataRilascio.text =""
                ///////////////////////////
                val url = FilmApplicationGlobal.GET_PICTURE_URL + film.posterPath
                filmItemRowBinding.filmProgressBar.visibility = View.VISIBLE
                Glide.with(this).load(url).into(filmItemRowBinding.filmImagine)
                filmItemRowBinding.filmProgressBar.visibility = View.GONE
                filmItemRowBinding.filmTitolo.text          = FilmApplicationGlobal.fixTooLongTitle(film.originalTitle.toString())
                filmItemRowBinding.filmOverview.text       =  film.overview
                filmItemRowBinding.filmDataRilascio.text    = film.releaseDate
                itemView.setOnClickListener{
                    dettaglioFilm(film.id.toString(), itemView)
                }

            }
        }
    }

    private fun dettaglioFilm(movie_id: String?, itemView: View? = null) {
        if (filmApplicationGlobal!!.retrofitService != null && movie_id != null ) {
            val call: Call<ResultDetailsFilm> = filmApplicationGlobal!!.retrofitService!!.getDettaglioFilm(
                movie_id!!
            )
            call.enqueue(object : Callback<ResultDetailsFilm> {
                override fun onResponse(
                    call: Call<ResultDetailsFilm>,
                    response: Response<ResultDetailsFilm>
                ) {
                    if (response.isSuccessful) {
                        println("Success dettaglioFilm!!")
                        Log.e(TAG, "SUCCESS!!")
                        filmApplicationGlobal!!.resultDetailsFilm = response.body() as ResultDetailsFilm
                        getActorsList(movie_id!!, itemView!!)
                    } else {
                        println("Errore getFilms() call retrofit")
                        Log.e(TAG, "NUMBER ERROR")
                    }
                }

                override fun onFailure(call: Call<ResultDetailsFilm>, t: Throwable) {
                    Log.e(TAG, "onFailure")
                    t.printStackTrace()
                }
            })
        }
    }

    private fun getActorsList(movie_id: String?, itemView: View? = null){
        if (filmApplicationGlobal!!.retrofitService != null) {
            val call: Call<ResultActorList> = filmApplicationGlobal!!.retrofitService!!.getActorsList(
                movie_id!!
            )
            call.enqueue(object : Callback<ResultActorList> {
                override fun onResponse(
                    call: Call<ResultActorList>,
                    response: Response<ResultActorList>
                ) {
                    if (response.isSuccessful) {
                        println("Success getActorsList!!")
                        Log.e(TAG, "SUCCESS!!")
                        filmApplicationGlobal!!.resultActorList = response.body() as ResultActorList
                        val intentDetailsFilm = Intent(
                            itemView!!.context,
                            FilmDetailsActivity::class.java
                        )
                        itemView!!.context.startActivity(intentDetailsFilm)
                    } else {
                        println("Errore getFilms() call retrofit")
                        Log.e(TAG, "CODE NUMBER ERROR")
                    }
                }
                override fun onFailure(call: Call<ResultActorList>, t: Throwable) {
                    Log.e(TAG, "onFailure getActorsList")
                    t.printStackTrace()
                }
            })
        }
    }

}

