package com.mvvm.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvvm.activities.ControllerFilmDetailsActivity
import com.mvvm.databinding.FilmItemRowBinding
import com.mvvm.model.Film
import com.mvvm.utils.FilmApplicationGlobal

class AdapterFilm(
    private val listaFilms: List<Film>,
) : RecyclerView.Adapter<AdapterFilm.FilmViewHolder?>() {

    private val TAG: String = AdapterFilm::class.java.simpleName
    lateinit var _itemView: View


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

    inner class FilmViewHolder(val filmItemRowBinding: FilmItemRowBinding) :
        RecyclerView.ViewHolder(filmItemRowBinding.root) {
        fun bind(film: Film) {
            with(itemView) {
                //reset values
                filmItemRowBinding.filmImagine.setImageBitmap(null)
                filmItemRowBinding.filmImagine.setImageDrawable(null)
                filmItemRowBinding.filmTitolo.text = ""
                filmItemRowBinding.filmOverview.text = ""
                filmItemRowBinding.filmDataRilascio.text = ""
                ///////////////////////////
                val url = FilmApplicationGlobal.GET_PICTURE_URL + film.posterPath
                filmItemRowBinding.filmProgressBar.visibility = View.VISIBLE
                Glide.with(this).load(url).into(filmItemRowBinding.filmImagine)
                filmItemRowBinding.filmProgressBar.visibility = View.GONE
                filmItemRowBinding.filmTitolo.text =
                    FilmApplicationGlobal.fixTooLongTitle(film.originalTitle.toString())
                filmItemRowBinding.filmOverview.text = film.overview
                filmItemRowBinding.filmDataRilascio.text = film.releaseDate
                _itemView = itemView
                itemView.setOnClickListener {
                    forwardDetails(film.id.toString())
                }

            }
        }
    }

    private fun forwardDetails(movie_id : String){
        val intentDetailsFilm = Intent(_itemView.context, ControllerFilmDetailsActivity::class.java)
        intentDetailsFilm.putExtra("movie_id", movie_id)
        _itemView.context.startActivity(intentDetailsFilm)
    }

}

