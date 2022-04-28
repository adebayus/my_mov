package com.sebade.relasiroom.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpResponse(
    var isSuccess : Boolean? = null,
    var isUsernameSame : Boolean? = null,
    var isEmailSame : Boolean? = null
) : Parcelable
