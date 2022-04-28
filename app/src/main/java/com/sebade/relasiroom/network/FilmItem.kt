package com.sebade.relasiroom.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmItem(

    @field:SerializedName("play")
    val play: List<PlayItem?>? = null,

    @field:SerializedName("director")
    val director: String? = null,

    @field:SerializedName("genre")
    val genre: String? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    //
    @field:SerializedName("judul")
    val judul: String? = null,

    @field:SerializedName("poster")
    val poster: String? = null,

    @field:SerializedName("desc")
    val desc: String? = null,

    //
    @field:SerializedName("directors")
    val directors: String? = null
) : Parcelable

