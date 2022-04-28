package com.sebade.relasiroom.model

import com.sebade.relasiroom.model.seat.SeatDetail

data class ListSeat(
    val seatD: List<SeatDetail?>? = null,
    val seatC: List<SeatDetail?>? = null,
    val seatB: List<SeatDetail?>? = null,
    val seatA: List<SeatDetail?>? = null
)

