package com.sebade.relasiroom.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sebade.relasiroom.ui.auth.LoginViewModel
import com.sebade.relasiroom.ui.auth.signup.PhotoScreenViewModel
import com.sebade.relasiroom.ui.auth.signup.SignUpViewModel
import com.sebade.relasiroom.ui.homepage.checkout.CheckoutViewModel
import com.sebade.relasiroom.ui.homepage.checkout.checkoutsuccess.CheckoutSuccessViewModel
import com.sebade.relasiroom.ui.homepage.dashboard.DashboardViewModel
import com.sebade.relasiroom.ui.ticket.TicketViewModel
import com.sebade.relasiroom.ui.ticket.detailtiket.DetailTiketViewModel

class LoginViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}

class DashboardViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}

class ModelFactory(val app : Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when(modelClass){
            CheckoutViewModel::class.java -> return CheckoutViewModel(app) as T
            TicketViewModel::class.java -> return TicketViewModel(app) as T
            DetailTiketViewModel::class.java -> return DetailTiketViewModel(app) as T
            SignUpViewModel::class.java -> return SignUpViewModel(app) as T
            PhotoScreenViewModel::class.java -> return PhotoScreenViewModel(app) as T
            CheckoutSuccessViewModel::class.java -> return CheckoutSuccessViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }

}