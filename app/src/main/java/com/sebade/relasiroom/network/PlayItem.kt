package com.sebade.relasiroom.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayItem(
    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable
