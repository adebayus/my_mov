package com.sebade.relasiroom.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateSaldoResponse(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable
