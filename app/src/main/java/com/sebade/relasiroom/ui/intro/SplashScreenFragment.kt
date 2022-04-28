package com.sebade.relasiroom.ui.intro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sebade.relasiroom.R


class SplashScreenFragment : Fragment() {

    private lateinit var splashViewModel: SplashScreenViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        splashViewModel = ViewModelProvider(requireActivity())[SplashScreenViewModel::class.java]
        splashViewModel.navigatedToActivity.observe(viewLifecycleOwner) {
            if (it == true) {
                Log.d("SplashFragment", "onCreateView: $it")
                requireView().findNavController()
                    .navigate(SplashScreenFragmentDirections.actionSplashFragmentToOnBoardingOne())
//                SplashFragmentDirections.actionSplashFragmentToOnBoardingOne()
                splashViewModel.doneNavigating()
            }
        }
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    companion object {
    }
}