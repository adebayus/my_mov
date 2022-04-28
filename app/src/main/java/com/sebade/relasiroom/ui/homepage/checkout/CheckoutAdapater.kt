package com.sebade.relasiroom.ui.homepage.checkout

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.CheckoutItemLayoutBinding
import com.sebade.relasiroom.databinding.NowPlayingLayoutItemBinding
import com.sebade.relasiroom.model.seat.SeatDetail

class CheckoutAdapater() : RecyclerView.Adapter<CheckoutAdapater.ViewHolder>() {

    private var list = emptyList<SeatDetail?>()

    fun setList(list: MutableList<SeatDetail?>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: CheckoutItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: SeatDetail?) {
            if (item != null) {
                with(binding){
                    tvPrice.text = "IDR 100.000"
                    tvSeatNumber.text = "Seat No ${item.seatNumber}"
                    ivSeatIcon.visibility = View.VISIBLE
                }
            }
            else {
                binding.tvSeatNumber.text = "Total yang harus dibayar "
                binding.tvPrice.text = "IDR 200.000"
                binding.ivSeatIcon.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<CheckoutItemLayoutBinding>(
            layoutInflater,
            R.layout.checkout_item_layout,
            parent,
            false
        )

        Log.d("TAG", "onCreateViewHolder: $list")
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}