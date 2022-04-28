package com.sebade.relasiroom.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.FragmentOnBoardingTwoBinding

class OnBoardingTwo : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentOnBoardingTwoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_on_boarding_two, container, false)
        // Inflate the layout for this fragment

        val viewModel = ViewModelProvider(requireActivity())[OnBoardingViewModel::class.java]

        binding.viewModel = viewModel

        viewModel.navigatedNext.observe(viewLifecycleOwner, Observer {
            if (it) {
                requireView().findNavController()
                    .navigate(OnBoardingTwoDirections.actionOnBoardingTwoToOnBoardingThree())
                viewModel.navigatedToNextDone()
            }
        })

        return binding.root
    }

    companion object {

    }
}