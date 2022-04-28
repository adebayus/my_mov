
package com.sebade.relasiroom.ui.intro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.FragmentOnBoardingOneBinding


class OnBoardingOne : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentOnBoardingOneBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_on_boarding_one, container, false
        )

        val viewModel = ViewModelProvider(requireActivity())[OnBoardingViewModel::class.java]

//        viewModel.setLayoutId(resources.getResourceEntryName(R.id.onboardingonelayout))

        viewModel.navigatedNext.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                requireView().findNavController().navigate(OnBoardingOneDirections.actionOnBoardingOneToOnBoardingTwo())
                viewModel.navigatedToNextDone()
            }
        })

        viewModel.navigatedLoginPage.observe(viewLifecycleOwner, Observer {
            if(it){
                requireView().findNavController().navigate(OnBoardingOneDirections.actionOnBoardingOneToRegisterNavigation())
                viewModel.navigatedToLoginPageDone()
            }
        })


        Log.d("TAG", "onCreateView: ${resources.getResourceEntryName(R.id.onboardingonelayout)}")
        binding.boardingOneViewModel = viewModel

        // Inflate the layout for this fragment
        return binding?.root
    }

    companion object {

    }
}