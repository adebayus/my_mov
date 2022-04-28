package com.sebade.relasiroom.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.sebade.relasiroom.database.modeldatabase.*

data class UserWithFilm (
    @Embedded val user : UserTable,
    @Relation(
        parentColumn = "username",
        entityColumn = "title",
        associateBy = Junction(UserFilmCrossRef::class)
    )
    val Film : List<FilmTable>
)
