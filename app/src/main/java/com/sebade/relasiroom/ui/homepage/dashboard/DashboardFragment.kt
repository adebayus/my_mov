package com.sebade.relasiroom.ui.homepage.dashboard

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
import com.sebade.relasiroom.databinding.FragmentDashboardBinding
import com.sebade.relasiroom.network.FilmItem
import com.sebade.relasiroom.utils.DashboardViewModelFactory
import com.sebade.relasiroom.utils.MarginItemDecoration
import kotlin.random.Random

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: DashboardViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        Log.d("TAG", "Random: ${Random(1).nextInt(10)} ")
        Log.d("TAG", "Random: ${Random(1).nextInt(-1,10)} ")
        viewModel = ViewModelProvider(
            requireActivity(),
            DashboardViewModelFactory(requireActivity().application)
        )[DashboardViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        /*viewModel.film.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_LONG).show()
            Log.d("TAG", "onCreateView: ${it}")
        })*/


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showComingSoon()
        showNowPlaying()
    }

    private fun showComingSoon() {
        binding.rvComingsoon.layoutManager =
            LinearLayoutManager(requireContext())
        val comingSoonAdapater = ComingSoonAdapater()
        binding.rvComingsoon.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.spaceRvItem
                ), null
            )
        )
        comingSoonAdapater.setOnClickCallback(object : ComingSoonAdapater.OnClickCallback {
            override fun onClickItem(data: FilmItem) {
                Toast.makeText(requireContext(), "$data", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.film.observe(viewLifecycleOwner, Observer {
            comingSoonAdapater.setListFilm(it)
        })

        binding.rvComingsoon.adapter = comingSoonAdapater
    }

    private fun showNowPlaying() {
        binding.rvNowplaying.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvNowplaying.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.spaceRvItem
                ), LinearLayoutManager.HORIZONTAL
            )
        )


        val nowPlayingAdapater = NowPlayingAdapater()
        nowPlayingAdapater.setOnClickCallback(object : NowPlayingAdapater.OnClickCallback {
            override fun onClickItem(data: FilmItem) {
                requireView().findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToDetailFilmFragment(data!!.title.toString()))
            }
        })

        viewModel.film.observe(viewLifecycleOwner, Observer {
            nowPlayingAdapater.setListFilm(it)
        })

        binding.rvNowplaying.adapter = nowPlayingAdapater
    }

    companion object {

    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "onResume: teset")
        viewModel.getAllData()
    }
}