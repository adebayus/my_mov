package com.sebade.relasiroom.ui.ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.ComingSoonLayoutItemBinding
import com.sebade.relasiroom.model.HistoryFilm

class TicketHistoryAdapter : RecyclerView.Adapter<TicketHistoryAdapter.ViewHolder>() {

    private var listHistory :List<HistoryFilm> = emptyList()
    private lateinit var onClickCallback: OnClickCallback

    fun setList(list : List<HistoryFilm>) {
        listHistory = list
        notifyDataSetChanged()
    }

    fun setOnClickCallback(onClickCallback: OnClickCallback){
        this.onClickCallback = onClickCallback
    }

    class ViewHolder(val binding: ComingSoonLayoutItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: HistoryFilm) {
            binding.tvTitle.text = item.title
            binding.tvGenre.text = item.genre
            binding.tvRating.text = item.rating
            Glide.with(itemView.context)
                .load(item.poster)
                .into(binding.ivPoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DataBindingUtil.inflate<ComingSoonLayoutItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.coming_soon_layout_item,
                parent,
                false
                )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listHistory[position]
        holder.itemView.setOnClickListener{
            onClickCallback.onClickItem(item)
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int = listHistory!!.size


    interface OnClickCallback {
        fun onClickItem(item : HistoryFilm)
    }
}
