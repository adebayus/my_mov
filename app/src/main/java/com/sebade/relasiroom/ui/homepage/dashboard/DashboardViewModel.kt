package com.sebade.relasiroom.ui.homepage.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sebade.relasiroom.database.DatabaseMov
import com.sebade.relasiroom.database.modeldatabase.ActorTable
import com.sebade.relasiroom.database.modeldatabase.FilmActorCrossRef
import com.sebade.relasiroom.database.modeldatabase.FilmTable
import com.sebade.relasiroom.network.ApiConfig
import com.sebade.relasiroom.network.FilmItem
import com.sebade.relasiroom.network.NetworkResponse
import com.sebade.relasiroom.repository.FilmActorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel(app: Application) : AndroidViewModel(app) {

    private val database = DatabaseMov.getDatabase(app)
    private val repository = FilmActorRepository(database)

    init {
        getAllData()
    }

    val film = repository.film
    val user = repository.user

    fun getAllData(){
        val client = ApiConfig.retrofitService.getAllData()
        client.enqueue(object : Callback<NetworkResponse> {
            override fun onResponse(
                call: Call<NetworkResponse>,
                response: Response<NetworkResponse>
            ) {
                if (response.isSuccessful) {
                    val responseFilm = response.body()!!.film
                    responseFilm!!.let {
                        viewModelScope.launch {
                            repository.refreshVideos(responseFilm)
                            /*insertToTable(responseFilm)*/
                        }
                    }
                }
            }

            override fun onFailure(call: Call<NetworkResponse>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message} ")
            }

        })

        viewModelScope.launch(Dispatchers.Main) {
            Log.d("TAG", " init init: ${repository.film.value} ")
            Log.d("TAG", " init : ${database.databaseDao.getActor()} ")
        }
    }

    fun profileOnClick() {

    }
}