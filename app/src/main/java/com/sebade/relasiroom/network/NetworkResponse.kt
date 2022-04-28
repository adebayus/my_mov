package com.sebade.relasiroom.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class NetworkResponse(

    @field:SerializedName("User")
    val user: List<UserItem?>? = null,

    @field:SerializedName("Film")
    val film: List<FilmItem?>? = null
) : Parcelable


