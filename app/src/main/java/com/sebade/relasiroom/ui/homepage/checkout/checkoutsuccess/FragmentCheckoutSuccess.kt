package com.sebade.relasiroom.ui.homepage.checkout.checkoutsuccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.FragmentCheckoutSuccessBinding
import com.sebade.relasiroom.utils.ModelFactory

class FragmentCheckoutSuccess : Fragment() {

    private lateinit var binding: FragmentCheckoutSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_checkout_success,
                container,
                false
            )

        val args = FragmentCheckoutSuccessArgs.fromBundle(requireArguments()).historyFilm

        val viewModel = ViewModelProvider(
            viewModelStore, ModelFactory(
                requireActivity().application,
            )
        )[CheckoutSuccessViewModel::class.java]

        viewModel.setArgs(args)

        viewModel.moveToFilmDetail.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.historyFilm.observe(viewLifecycleOwner) { hFilm ->
                    requireView().findNavController()
                        .navigate(
                            FragmentCheckoutSuccessDirections
                                .actionFragmentCheckoutSuccessToDetailTiketFragment2(hFilm)
                        )
                }
            }
        }

        viewModel.moveToHome.observe(viewLifecycleOwner) {
            if (it) {
                requireView().findNavController()
                    .navigate(
                        FragmentCheckoutSuccessDirections
                            .actionFragmentCheckoutSuccessToDashboardFragment()
                    )
            }
        }


        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

}