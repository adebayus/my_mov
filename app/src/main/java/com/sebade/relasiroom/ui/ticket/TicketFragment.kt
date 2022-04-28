package com.sebade.relasiroom.ui.ticket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.FragmentTicketBinding
import com.sebade.relasiroom.model.HistoryFilm
import com.sebade.relasiroom.utils.MarginItemDecoration
import com.sebade.relasiroom.utils.ModelFactory


class TicketFragment : Fragment() {

    private lateinit var binding: FragmentTicketBinding
    private lateinit var rvAdapter: TicketHistoryAdapter
    private lateinit var viewModel: TicketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket, container, false)
        // Inflate the layout for this fragment
        rvAdapter = TicketHistoryAdapter()

        viewModel = ViewModelProvider(
            requireActivity(),
            ModelFactory(requireActivity().application)
        )[TicketViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.user.observe(viewLifecycleOwner){
            it.username?.let { it1 -> viewModel.getHistoryTicketApi(it1) }
        }

       /* viewModel.historyFilm.observe(viewLifecycleOwner){
            binding.tvTotalMovies.text = it.size.toString()
        }
*/

        binding.rvHistoryTicket.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistoryTicket.adapter = rvAdapter
        binding.rvHistoryTicket.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.spaceRvItem
                ), null
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.historyFilm.observe(viewLifecycleOwner){
            Log.d("pepe", "onViewCreated: $it ")
            showRv(it)
        }
    }

    private fun showRv(it: List<HistoryFilm>?) {
        with(binding){
            rvAdapter = TicketHistoryAdapter()
            rvAdapter.setList(it!!)
            rvAdapter.setOnClickCallback(object : TicketHistoryAdapter.OnClickCallback{
                override fun onClickItem(item: HistoryFilm) {
                    requireView().findNavController().navigate(TicketFragmentDirections.actionTicketFragmentToDetailTiketFragment(item))
                }
            })
            rvHistoryTicket.layoutManager = LinearLayoutManager(requireContext())
            rvHistoryTicket.adapter = rvAdapter

        }
    }

    companion object {

    }
}