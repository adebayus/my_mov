package com.sebade.relasiroom.ui.ticket

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.sebade.relasiroom.database.DatabaseMov
import com.sebade.relasiroom.model.HistoryFilm
import com.sebade.relasiroom.network.ApiConfig
import com.sebade.relasiroom.network.FilmItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class TicketViewModel(app: Application) : AndroidViewModel(app) {

    private var database = DatabaseMov.getDatabase(app)
    private var repository = TicketRepository(database)
    val user = repository.username

    val historyFilm = repository.historyList
    val historySize: LiveData<String> = Transformations.map(historyFilm) {
        "${it.size.toString()} + Movies"
    }

    fun getHistoryTicketApi(username: String) {
        val client = ApiConfig.retrofitService.getUserTransaction(username)
        client.enqueue(object : retrofit2.Callback<List<HistoryFilm>> {
            override fun onResponse(
                call: Call<List<HistoryFilm>>,
                response: Response<List<HistoryFilm>>
            ) {
                if (response.isSuccessful) {
                    val responseValue = response.body()

                    viewModelScope.launch(Dispatchers.IO) {
                        if (responseValue != null) {
                            repository.refresh(responseValue)
                        }else {
                            repository.deleteAll()
                        }
                    }
                    Log.d("fucker", "onResponse: ${response.body()}")
                    /*database.databaseDao.insertRefUserFilm()*/
                }

            }

            override fun onFailure(call: Call<List<HistoryFilm>>, t: Throwable) {
                Log.d("fucker", "onFailure: ${t.message}")
            }

        })
    }

}