package com.sebade.relasiroom.ui.homepage.detailfilm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.ComingSoonLayoutItemBinding
import com.sebade.relasiroom.databinding.WhoPlayedLayoutItemBinding
import com.sebade.relasiroom.network.FilmItem
import com.sebade.relasiroom.network.PlayItem

class WhoPlayedAdapter() : RecyclerView.Adapter<WhoPlayedAdapter.ViewHolder>() {

    var actorList = emptyList<PlayItem?>()

    fun setListActor(actor : List<PlayItem?>){
        actorList = actor
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: WhoPlayedLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item : PlayItem) {
            binding.tvName.text = item.nama
            Glide.with(itemView.context)
                .load(item.url)
                .error(R.drawable.user_pic)
                .into(binding.ivProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DataBindingUtil.inflate<WhoPlayedLayoutItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.who_played_layout_item,
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = actorList[position]
        holder.bind(item!!)
    }

    override fun getItemCount(): Int {
        return actorList.size
    }

}