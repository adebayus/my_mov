package com.sebade.relasiroom.ui.homepage.choseseat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.FragmentChooseSeatBinding


class ChooseSeatFragment : Fragment() {

    private lateinit var binding: FragmentChooseSeatBinding
    private lateinit var viewModel: ChooseSeatViewModel
    private val args by lazy {
        ChooseSeatFragmentArgs.fromBundle(requireArguments()).film
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose_seat, container, false)
        viewModel = ViewModelProvider(
            requireActivity(),
            ChooseSeatViewModelFactory(requireActivity().application)
        )[ChooseSeatViewModel::class.java]

        viewModel.setArguments(args)


        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel



        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel._navigated.observe(viewLifecycleOwner) {
            if (it == true) {
                Log.d("TAG", "onViewCreated: ${viewModel.listBuyTiket}")
                requireView().findNavController().navigate(ChooseSeatFragmentDirections.actionChooseSeatFragmentToCheckoutFragment(
                    viewModel.listBuyTiket.toTypedArray(),
                    args
                ))
                viewModel.navigatedToCheckoutDone()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.resetListBuy()
    }
    companion object {

    }
}