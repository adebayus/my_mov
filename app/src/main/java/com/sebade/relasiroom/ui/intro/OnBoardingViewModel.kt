package com.sebade.relasiroom.ui.intro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class OnBoardingViewModel : ViewModel() {

    private lateinit var layout_id: String

    private val _navigatedNext = MutableLiveData<Boolean>(false)
    var navigatedNext: LiveData<Boolean> = _navigatedNext

    private val _navigatedLoginPage = MutableLiveData<Boolean>(false)
    var navigatedLoginPage: LiveData<Boolean> = _navigatedLoginPage

//    init {
//        Log.d("TAG", ": ${R.id.onBoardingOne}")
//    }

//    fun setLayoutId(layoutName: String) {
//        layout_id = layoutName
//
//    }

    fun btnContinue() {
        Log.d("TAG", "btnContinue onClick ")
//        when (layout_id){
//            "${R.id.onBoardingOne}" -> Log.d("TAG", "btnContinue: bisa gan ")
//        }
        _navigatedNext.value = true
    }

    fun navigatedToNextDone() {
        _navigatedNext.value = false
    }

    fun btnNavigatedToLoginPage() {
        Log.d("TAG", "btnNavigatedToLoginPage: onClick")
        _navigatedLoginPage.value = true
    }

    fun navigatedToLoginPageDone() {
        _navigatedLoginPage.value = false
    }
}