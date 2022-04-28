package com.sebade.relasiroom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpFormModel(
    var username: FormValidation? = null,
    var password: FormValidation? = null,
    var nama: FormValidation? = null,
    var email: FormValidation? = null,
) : Parcelable

