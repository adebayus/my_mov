package com.sebade.relasiroom.ui.auth.signup

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.sebade.relasiroom.R
import com.sebade.relasiroom.databinding.FragmentPhotoScreenBinding
import com.sebade.relasiroom.utils.ModelFactory

class PhotoScreenFragment : Fragment() {

    private var startProfileImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data
                viewModel.setUri(fileUri)
                viewModel.isSetImage.value = true
                Log.d("spi", ": $fileUri")
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Log.d("spi", ": ${ImagePicker.getError(data)}")
            } else {
                Log.d("spi", "task Canceled: ")
            }
            viewModel.choosePhotoOnClick.value = false
        }


    private var permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            var allAreGranted = true
            for (b in result.values) {
                allAreGranted = allAreGranted && b
            }
            if (allAreGranted) {
                Log.d("spi", "onCreateView: before onClick")
                ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .createIntent {
                        startProfileImage.launch(it)
                    }
                Log.d("spi", "onCreateView: after onClick")
//                .start()
            } else {
                Log.d("spi", "not Granted: ")
                viewModel.choosePhotoOnClick.value = false
            }

        }

    private lateinit var viewModel: PhotoScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPhotoScreenBinding>(
            inflater,
            R.layout.fragment_photo_screen,
            container,
            false
        )

        val args = PhotoScreenFragmentArgs.fromBundle(requireArguments()).newUser

        viewModel = ViewModelProvider(
            viewModelStore, ModelFactory(
                requireActivity().application
            )
        )[PhotoScreenViewModel::class.java]
        viewModel.setNewUser(args)
        viewModel.choosePhotoOnClick.observe(viewLifecycleOwner) {
            if (it) {
                val appPerms = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
                permissionLauncher.launch(appPerms)
            }
        }
        viewModel.moveToDashBoard.observe(viewLifecycleOwner){
            if(it){
                requireView().findNavController().navigate(PhotoScreenFragmentDirections.actionPhotoScreenFragmentToBottomNavNavigation())
            }
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {

    }
}