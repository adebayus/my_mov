package com.sebade.relasiroom.database.modeldatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class UserTable(



    val password: String? = null,

    val nama: String? = null,

    val saldo: String? = null,

    val email: String? = null,

    val url: String? = null,

    val username: String,
    @PrimaryKey(autoGenerate = false)
    val id : Int = 0,
)
