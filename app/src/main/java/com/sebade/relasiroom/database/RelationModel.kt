package com.sebade.relasiroom.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.sebade.relasiroom.database.modeldatabase.FilmActorCrossRef
import com.sebade.relasiroom.database.modeldatabase.FilmTable
import com.sebade.relasiroom.database.modeldatabase.ActorTable

data class FilmWithPlayer (
    @Embedded val film : FilmTable,
    @Relation(
        parentColumn = "title",
        entityColumn = "nama",
        associateBy = Junction(FilmActorCrossRef::class)
    )
    val actor : List<ActorTable>
)

data class PlayerWithFilm (
    @Embedded val actor : ActorTable,
    @Relation(
        parentColumn = "nama",
        entityColumn = "title",
        associateBy = Junction(FilmActorCrossRef::class)
    )
    val film : List<FilmTable>
)