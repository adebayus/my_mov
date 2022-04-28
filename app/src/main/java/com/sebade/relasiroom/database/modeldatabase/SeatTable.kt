package com.sebade.relasiroom.database.modeldatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seat_table")
data class SeatTable(
    @PrimaryKey(autoGenerate = false)
    var id : Int,
    var userId : Int,
    var isBooked: Int,
    val row: String,
    val seatNumber: Int,
)
