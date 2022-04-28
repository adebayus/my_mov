package com.sebade.relasiroom.ui.homepage.detailfilm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.FragmentDetailFilmBinding
import com.sebade.relasiroom.utils.MarginItemDecoration


class DetailFilmFragment : Fragment() {

    private lateinit var binding: FragmentDetailFilmBinding
    private lateinit var viewModel: DetailFilmModel
    private lateinit var whoPlayedAdapter: WhoPlayedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentDetailFilmBinding>(
            inflater,
            R.layout.fragment_detail_film,
            container,
            false
        )

        val args = DetailFilmFragmentArgs.fromBundle(requireArguments())

        whoPlayedAdapter = WhoPlayedAdapter()
        Log.d("TAG", "onCreateView: $args")

        viewModel = ViewModelProvider(
            requireActivity(),
            DetailFilmFactory(requireActivity().application)
        )[DetailFilmModel::class.java]


        viewModel.setArguments(args.primaryKey)

        viewModel.navigatedPilihKursi.observe(viewLifecycleOwner){
            if (it){
                val film = viewModel.film.value
                requireView().findNavController().navigate(DetailFilmFragmentDirections.actionDetailFilmFragmentToChooseSeatFragment(film))
                viewModel.navigatedPilihKursiDone()
            }
        }

        viewModel.onBackIconPressed.observe(viewLifecycleOwner){
            if(it){
                requireView().findNavController().navigateUp()
                viewModel.onBackPressedDone()
            }
        }


        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvPlayer.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        viewModel.film!!.observe(viewLifecycleOwner){ filmItem ->
            Log.d("TAG", "onViewCreated: $filmItem ")
            filmItem!!.play!!.let {
                Log.d("TAG", "onViewCreated: masuk ")
                whoPlayedAdapter.setListActor(it)
            }
        }
        binding.rvPlayer.adapter = whoPlayedAdapter


        viewModel.onBackIconPressed.observe(viewLifecycleOwner){
            if(it){

            }
        }

    }


    companion object {

    }
}