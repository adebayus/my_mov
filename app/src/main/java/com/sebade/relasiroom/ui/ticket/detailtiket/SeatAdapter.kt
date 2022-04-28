package com.sebade.relasiroom.ui.ticket.detailtiket

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.CheckoutItemLayoutBinding
import com.sebade.relasiroom.model.seat.SeatDetail

class SeatAdapter() : RecyclerView.Adapter<SeatAdapter.ViewHolder>() {

    private var list = emptyList<SeatDetail>()

    class ViewHolder(val binding: CheckoutItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: SeatDetail) {
            changeStyle()
            with(binding){
                tvSeatNumber.text = "Seat Number " + item.row + item.seatNumber
            }
        }

        private fun changeStyle() {
            binding.constraintLayout.background =
                AppCompatResources.getDrawable(binding.constraintLayout.context, R.color.white)
            binding.tvSeatNumber.setTextColor(
                AppCompatResources.getColorStateList(
                    binding.tvSeatNumber.context,
                    R.color.darkBlue
                )
            )
            binding.tvPrice.setTextColor(
                AppCompatResources.getColorStateList(
                    binding.tvPrice.context,
                    R.color.darkBlue
                )
            )
        }
    }

    fun setListDetail(list: List<SeatDetail>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<CheckoutItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.checkout_item_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}