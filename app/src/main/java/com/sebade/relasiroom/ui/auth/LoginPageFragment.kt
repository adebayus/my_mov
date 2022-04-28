package com.sebade.relasiroom.ui.auth

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

import com.sebade.relasiroom.databinding.FragmentLoginPageBinding
import com.sebade.relasiroom.ui.auth.signup.SignUpViewModel
import com.sebade.relasiroom.utils.LoginViewModelFactory

//import com.sebade.mymovieticket.utils.Factory

class LoginPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentLoginPageBinding>(
            inflater,
            R.layout.fragment_login_page,
            container,
            false
        )

        val viewModel = ViewModelProvider(
            requireActivity(),
            LoginViewModelFactory(requireActivity().application)
        )[LoginViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.isUserFound.observe(viewLifecycleOwner, Observer {
            if(it){
                requireView().findNavController().navigate(LoginPageFragmentDirections.actionLoginPageFragmentToBottomNavNavigation())
                viewModel.navigatedHomepageDone()
            }
        })
        viewModel.navigatedToSignUpFragment.observe(viewLifecycleOwner){
            if (it){
                requireView().findNavController().navigate(LoginPageFragmentDirections.actionLoginPageFragmentToSignUpFragment())
//                requireView().findNavController().navigate(LoginPageFragmentDirections.actionLoginPageFragmentToPhotoScreenFragment())
                viewModel.navigatedToSignUpFragment.value = false
            }
        }


       /* val viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]


        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.isUserFound.observe(viewLifecycleOwner, Observer {
            if (it) {
                requireView().findNavController()
//                    .navigate(LoginPageFragmentDirections.actionLoginPageFragmentToHomePageNavigation())
                    .navigate(LoginPageFragmentDirections.actionLoginPageFragmentToGeneralBottomNav())
                viewModel.navigatedHomepageDone()

            }
        })*/

        return binding.root
    }

    companion object {

    }
}


//override fun onResponse(
////                call: Call<ResponseGetUser>,
////                response: Response<ResponseGetUser>
////            ) {
////                if (response.isSuccessful){
////
////                }
////            }
////
////            override fun onFailure(call: Call<ResponseGetUser>, t: Throwable) {
////
////            }