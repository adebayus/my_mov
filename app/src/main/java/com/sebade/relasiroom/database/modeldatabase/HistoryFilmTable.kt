package com.sebade.relasiroom.database.modeldatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_film_table")
data class HistoryFilmTable(
    val director: String? = null,

    val genre: String? = null,

    val rating: String? = null,

    val title: String,

    val judul: String? = null,

    val poster: String? = null,

    val desc: String? = null,

    val directors: String? = null,

    @PrimaryKey(autoGenerate = false)
    var id: Int

)