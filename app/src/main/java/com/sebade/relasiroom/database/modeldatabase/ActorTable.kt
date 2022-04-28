package com.sebade.relasiroom.database.modeldatabase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "actor_table")
data class ActorTable(
    @PrimaryKey
    val nama: String,
    val url: String? = null
)
