package com.sebade.relasiroom.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserItem(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("saldo")
	val saldo: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("url")
	var url: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
