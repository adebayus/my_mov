package com.sebade.relasiroom.ui.homepage.checkout

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sebade.relasiroom.database.DatabaseMov
import com.sebade.relasiroom.model.BackdropState
import com.sebade.relasiroom.model.HeaderBeliTiket
import com.sebade.relasiroom.model.HistoryFilm
import com.sebade.relasiroom.model.seat.SeatDetail
import com.sebade.relasiroom.network.ApiConfig
import com.sebade.relasiroom.network.FilmItem
import com.sebade.relasiroom.network.UserItem
import com.sebade.relasiroom.repository.CheckoutRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class CheckoutViewModel(application: Application) : AndroidViewModel(application) {

    val database = DatabaseMov.getDatabase(application)
    val repository = CheckoutRepository(database)

    val listOrder = MutableLiveData<MutableList<SeatDetail?>>(null)
    val headerBeliTiket = MutableLiveData<HeaderBeliTiket>(HeaderBeliTiket("Checkout", null))
    private val _saldoCukup = MutableLiveData<Boolean>()

    val saldoCukup: LiveData<Boolean> = _saldoCukup
    private val updateSaldoDone = MutableLiveData<Boolean>()

    private val indexUser = MutableLiveData<Int>()
    val navigatedToFragment = MutableLiveData<Boolean>()

    val loading = MutableLiveData<BackdropState>(BackdropState(false))
    val suck = repository.user

    var postFilmResponse = MutableLiveData<HistoryFilm>()

    private var film: FilmItem? = null

    private var listTransaction: MutableList<HistoryFilm>? = mutableListOf()

    init {
        getUser()
    }


    fun getUserTransaction(username: String) {
        Log.d("pay", "getUserTransaction: $username")
        val client = ApiConfig.retrofitService.getUserTransaction(username)
//        val client = suck.value?.username?.let { ApiConfig.retrofitService.getUseTransaction(it) }
        client.enqueue(object : retrofit2.Callback<List<HistoryFilm>> {
            override fun onResponse(
                call: Call<List<HistoryFilm>>,
                response: Response<List<HistoryFilm>>
            ) {
                if (response.isSuccessful) {
                    val responseValue = response.body()
                    if(responseValue != null) {
                        listTransaction = responseValue as MutableList<HistoryFilm>?
                    }
                    Log.d("pay", "onResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<List<HistoryFilm>>, t: Throwable) {
                Log.d("pay", "on fail: ${t.message}")
            }
        })

    }

    fun navigatedDone() {
        navigatedToFragment.value = false
    }

    fun setListOrder(list: List<SeatDetail>, film: FilmItem?) {
        val mutableList = mutableListOf<SeatDetail?>()
        list.forEach {
            mutableList.add(it)
        }
        mutableList.add(null)
        listOrder.value = mutableList
        this.film = film
    }

    fun payOnClick() {
        loading.value = BackdropState(true)
        if (saldoCukup.value == true) {
            val currentSaldo = suck.value?.saldo?.toInt()?.minus(200000)
            val valueBody = Gson().toJson(with(suck.value) {
                UserItem(
                    this?.password,
                    this?.nama,
                    currentSaldo.toString(),
                    this?.email,
                    this?.url,
                    this?.username
                )
            })
            val requestBody =
                valueBody.toRequestBody("application/json; charset=utf-8".toMediaType())
            val client =
                ApiConfig.retrofitService.updateSaldo(requestBody, indexUser.value.toString())
            client.enqueue(object : retrofit2.Callback<UserItem> {
                override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {
                    if (response.isSuccessful) {
                        Log.d("payonClick", "onResponse: ${response.body()} ")
                        postTransaction()
                    }
                }

                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                    loading.value = BackdropState(false)
                    Log.d("Tag", "${t.message}")
                }
            })
        }
    }

    private fun postTransaction() {
        val seatList = mutableListOf<SeatDetail>()
        viewModelScope.launch {
            val filmItem = repository.getFilmByKey(film?.title.toString())
            listOrder.value?.forEach {
                Log.d("TAG", "postTransaction: $it ")
                if (it is SeatDetail) {
                    seatList.add(it)
                }
            }

            Log.d("pepea", "postTransaction: $seatList ")
            val historyFilm = HistoryFilm(
                filmItem.director,
                filmItem.genre,
                filmItem.rating,
                filmItem.title,
                filmItem.judul,
                filmItem.poster,
                filmItem.desc,
                filmItem.directors,
                seatList
            )
            listTransaction?.add(historyFilm)

            Log.d("pepe", "postTransaction: $listTransaction")
            val requestBodyValue = Gson().toJson(listTransaction)
            val requestBody =
                requestBodyValue.toRequestBody("application/json; charset=utf-8".toMediaType())
            val client = ApiConfig.retrofitService.postArrayTransaction(
                requestBody,
                suck.value?.username.toString()
            )
            client.enqueue(object : retrofit2.Callback<List<HistoryFilm>> {
                override fun onResponse(
                    call: Call<List<HistoryFilm>>,
                    response: Response<List<HistoryFilm>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("payonClick", "postTransaction: ${response.body()}: ")
                        postFilmResponse.value = historyFilm
                        loading.value = BackdropState(false)
                        navigatedToFragment.value = true
                    }
                }

                override fun onFailure(call: Call<List<HistoryFilm>>, t: Throwable) {
                    Log.d("payonClick", "postTransaction: fail ${t.message}: ")
                    loading.value = BackdropState(false)
                }

            })

        }
    }

    fun getUser() {
        loading.value = BackdropState(true)
        val client = ApiConfig.retrofitService.getUserData()
        client.enqueue(object : retrofit2.Callback<List<UserItem?>> {
            override fun onResponse(
                call: Call<List<UserItem?>>,
                response: Response<List<UserItem?>>
            ) {
                val listUser = response.body()
                if (!listUser.isNullOrEmpty()) {
                    val index = listUser.indexOfFirst {
                        it?.username == suck.value?.username.toString()
                    }
                    indexUser.value = index
                    _saldoCukup.value = suck.value?.saldo?.toInt()!! >= 20000
                    viewModelScope.launch {
                        delay(4000)
                        loading.value = BackdropState(false)
                    }
                    Log.d("TAG", "onResponse: ${response.body()}, $index")
                }
            }

            override fun onFailure(call: Call<List<UserItem?>>, t: Throwable) {
                Log.d("TAG", "onResponse: ${t.message}")
            }
        })
    }


}

