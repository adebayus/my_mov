package com.sebade.relasiroom.ui.ticket.detailtiket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.FragmentDetailTiketBinding
import com.sebade.relasiroom.model.seat.SeatDetail
import com.sebade.relasiroom.utils.ModelFactory


class DetailTiketFragment : Fragment() {

    private lateinit var binding: FragmentDetailTiketBinding
    private lateinit var viewModel: DetailTiketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail_tiket,
            container,
            false
        )
        viewModel = ViewModelProvider(
            viewModelStore,
            ModelFactory(requireActivity().application)
        )[DetailTiketViewModel::class.java]
        val args = DetailTiketFragmentArgs.fromBundle(requireArguments()).detailFilm
        viewModel.setTicketSeat(args)

        val adapter = SeatAdapter()
        binding.rvTicketDetail.layoutManager = LinearLayoutManager(requireContext())
        viewModel.ticketSeat.observe(viewLifecycleOwner){
            if (it != null) {
                Log.d("detail", "onCreateView: ${it.seatDetail}")
                Log.d("detail", "onCreateView: ${it}")
                val list = it.seatDetail as List<SeatDetail>
                adapter.setListDetail(list)
            }
        }
        binding.rvTicketDetail.adapter = adapter

        Log.d("DetailTiketFragmentArgs", "onCreateView: args")

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        // Inflate the layout for this fragment
        return binding.root
    }


}