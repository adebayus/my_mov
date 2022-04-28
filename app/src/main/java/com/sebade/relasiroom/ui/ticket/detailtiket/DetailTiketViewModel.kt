package com.sebade.relasiroom.ui.ticket.detailtiket

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sebade.relasiroom.model.HistoryFilm
import com.sebade.relasiroom.model.seat.SeatDetail

class DetailTiketViewModel(var app : Application) : AndroidViewModel(app) {
    private var _ticketSeat = MutableLiveData<HistoryFilm>()
    var ticketSeat : LiveData<HistoryFilm> = _ticketSeat

    fun setTicketSeat(historyFilm : HistoryFilm){
        _ticketSeat.value = historyFilm
    }

}