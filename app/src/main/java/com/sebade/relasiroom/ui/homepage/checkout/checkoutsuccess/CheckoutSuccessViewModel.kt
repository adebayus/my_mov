package com.sebade.relasiroom.ui.homepage.checkout.checkoutsuccess

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sebade.relasiroom.model.HistoryFilm

class CheckoutSuccessViewModel(val app : Application) : AndroidViewModel(app){

    var moveToHome = MutableLiveData<Boolean>()
    var moveToFilmDetail = MutableLiveData<Boolean>()
    var historyFilm = MutableLiveData<HistoryFilm>()

    fun btnHome() {
        moveToHome.value = true
    }
    fun btnDetailFilm() {
        moveToFilmDetail.value = true
    }

    fun setArgs(hFilm : HistoryFilm){
        historyFilm.value = hFilm
    }



}