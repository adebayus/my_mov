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
import com.sebade.relasiroom.databinding.FragmentOnBoardingThreeBinding

class OnBoardingThree : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentOnBoardingThreeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_on_boarding_three, container, false)

        val viewModel = ViewModelProvider(requireActivity())[OnBoardingViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.navigatedLoginPage.observe(viewLifecycleOwner, Observer {
            if (it){
                requireView().findNavController().navigate(OnBoardingThreeDirections.actionOnBoardingThreeToRegisterHostActivity())
                viewModel.navigatedToLoginPageDone()
            }
        })



        return binding?.root
    }

    companion object {
    }
}