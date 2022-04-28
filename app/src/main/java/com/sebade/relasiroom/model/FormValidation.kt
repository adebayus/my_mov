package com.sebade.relasiroom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FormValidation(
    var isErr : Boolean? = null,
    var errorText : String? =  null
):Parcelable