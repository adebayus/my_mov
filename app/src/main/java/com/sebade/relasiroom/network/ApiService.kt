package com.sebade.relasiroom.network

import com.sebade.relasiroom.model.HistoryFilm
import com.sebade.relasiroom.model.UserTransaction
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET(".json")
    fun getAllData(): Call<NetworkResponse>

    @PUT("User/{id}.json")
    fun updateSaldo(
        @Body rBody: RequestBody,
        @Path("id") id: String
    ): Call<UserItem>

    @GET("User.json")
    fun getUserData(): Call<List<UserItem?>>

    @GET("transaksi_user/{username}.json")
    fun getUserTransaction(
        @Path("username") username : String
    ) : Call<List<HistoryFilm>>

    @PUT("transaksi_user/{username}.json")
    fun postArrayTransaction(
        @Body rBody: RequestBody,
        @Path("username") username : String
    ) : Call<List<HistoryFilm>>

    @PUT("User.json")
    fun postUser(
        @Body rBody : RequestBody,
    ) : Call<List<UserItem?>?>

}