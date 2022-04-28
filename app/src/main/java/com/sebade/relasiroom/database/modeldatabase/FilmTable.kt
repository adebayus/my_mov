package com.sebade.relasiroom.database.modeldatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film_table")
data class FilmTable(
    val director: String? = null,

    val genre: String? = null,

    val rating: String? = null,

    @PrimaryKey
    val title: String,

    val judul: String? = null,

    val poster: String? = null,

    val desc: String? = null,

    val directors: String? = null
)
