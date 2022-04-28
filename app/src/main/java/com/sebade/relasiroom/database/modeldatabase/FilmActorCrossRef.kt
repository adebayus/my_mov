package com.sebade.relasiroom.database.modeldatabase

import androidx.room.Entity

@Entity(primaryKeys = ["title", "nama"],tableName = "filmactorcrossref_table")
data class FilmActorCrossRef(
    val title: String,
    val nama: String,
)
