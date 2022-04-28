package com.sebade.relasiroom.ui.homepage.dashboard

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.NowPlayingLayoutItemBinding
import com.sebade.relasiroom.network.FilmItem

class NowPlayingAdapater() : RecyclerView.Adapter<NowPlayingAdapater.ViewHolder>() {

    private lateinit var onClickCallback: OnClickCallback
    private var listFilmItem = emptyList<FilmItem>()

    fun setOnClickCallback(onClickCallback: OnClickCallback){
        this.onClickCallback = onClickCallback
    }

    fun setListFilm(list : List<FilmItem>){
        this.listFilmItem = list
        Log.d("TAG", "setListFilm: ${this.listFilmItem}")
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: NowPlayingLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: FilmItem){
            binding.tvTitle.text = item.title
            binding.tvGenre.text = item.genre
            binding.tvRating.text = item.rating

            Glide.with(itemView)
                .load(item.poster)
                .into(binding.ivPoster)
        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DataBindingUtil.inflate<NowPlayingLayoutItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.now_playing_layout_item,
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listFilmItem[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickCallback.onClickItem(item)
        }

    }

    override fun getItemCount(): Int {
        return listFilmItem.size
    }

    interface OnClickCallback {
        fun onClickItem(data : FilmItem)
    }
}

