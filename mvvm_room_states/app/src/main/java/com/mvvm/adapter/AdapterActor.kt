package com.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvvm.databinding.ActorRowBinding
import com.mvvm.model.Cast
import com.mvvm.utils.FilmApplicationGlobal

class AdapterActor(listActors: List<Cast>) : RecyclerView.Adapter<AdapterActor.ActorViewHolder?>() {

    private val TAG: String = AdapterActor::class.java.simpleName
    var listActors: List<Cast>? = null

    init {
        this.listActors = listActors
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ActorViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(viewGroup.context)
        var view = ActorRowBinding.inflate(inflater, viewGroup, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(listActors!![position])
    }

    override fun getItemCount(): Int {
        return listActors!!.size
    }

    inner class ActorViewHolder(val actorRowBinding: ActorRowBinding) :
        RecyclerView.ViewHolder(actorRowBinding.root) {
        fun bind(castActor: Cast) {
            with(itemView) {
                val url = FilmApplicationGlobal.GET_PICTURE_URL + castActor.profilePath
                Glide.with(this).load(url).into(actorRowBinding.actorImageView)
                actorRowBinding.actorName.text = castActor.originalName.toString().trim()
                actorRowBinding.actorRole.text = castActor.character.toString()
            }
        }
    }
}