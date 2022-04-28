package com.sebade.relasiroom.ui.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel : ViewModel() {

    private var _navigatedToActivity = MutableLiveData<Boolean>()

    val navigatedToActivity : LiveData<Boolean>
        get() {
            return _navigatedToActivity
        }

    init {
        splashScreen()
    }

    private fun splashScreen(){
        viewModelScope.launch {
            delay(3000)
            _navigatedToActivity.value = true
        }
    }

    fun doneNavigating(){
        _navigatedToActivity.value = false
    }

}