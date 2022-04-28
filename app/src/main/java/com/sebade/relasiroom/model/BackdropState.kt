package com.sebade.relasiroom.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BackdropState(
    var loading : Boolean?
) : Parcelable
