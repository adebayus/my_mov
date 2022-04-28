package com.sebade.relasiroom.ui.auth.signup

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
import com.sebade.relasiroom.databinding.FragmentSignUpBinding
import com.sebade.relasiroom.utils.ModelFactory

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)

        viewModel = ViewModelProvider(
            viewModelStore,
            ModelFactory(requireActivity().application)
        )[SignUpViewModel::class.java]

        viewModel.navigatedToFoto.observe(viewLifecycleOwner){
            if (it){
                Log.d("po", "onCreateView: $it")
                viewModel.newUser.observe(viewLifecycleOwner){ userItem ->
                    Log.d("pos", "onCreateView: $userItem")
                    requireView().findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToPhotoScreenFragment(userItem))
                    viewModel.navigatedToFoto.value = false
                }
            }
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    companion object {

    }
}