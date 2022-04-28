package com.sebade.relasiroom.model.seat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeatDetail(
    var isBooked: Int,
    val row: String,
    val seatNumber: Int,
) : Parcelable