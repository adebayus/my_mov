package com.sebade.relasiroom.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sebade.relasiroom.model.seat.SeatDetail
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryFilm(
    @field:SerializedName("director")
    val director: String? = null,

    @field:SerializedName("genre")
    val genre: String? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("judul")
    val judul: String? = null,

    @field:SerializedName("poster")
    val poster: String? = null,

    @field:SerializedName("desc")
    val desc: String? = null,

    @field:SerializedName("directors")
    val directors: String? = null,

    @field:SerializedName("seatDetail")
    val seatDetail : MutableList<SeatDetail>? = null
) : Parcelable
