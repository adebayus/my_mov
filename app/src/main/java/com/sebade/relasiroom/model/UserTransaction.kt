package com.sebade.relasiroom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserTransaction(
    var title : String,
    var judul : String
) : Parcelable
