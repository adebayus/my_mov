package com.sebade.relasiroom.database

import androidx.room.Embedded
import androidx.room.Relation
import com.sebade.relasiroom.database.modeldatabase.HistoryFilmTable
import com.sebade.relasiroom.database.modeldatabase.SeatTable
import com.sebade.relasiroom.model.HistoryFilm


data class HistoryFilmWithSeat(
    @Embedded val historyFilmTable: HistoryFilmTable,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val seatTable : MutableList<SeatTable>
)
