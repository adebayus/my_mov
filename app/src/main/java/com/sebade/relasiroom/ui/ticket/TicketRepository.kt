package com.sebade.relasiroom.ui.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sebade.relasiroom.database.DatabaseMov
import com.sebade.relasiroom.database.HistoryFilmWithSeat
import com.sebade.relasiroom.database.modeldatabase.HistoryFilmTable
import com.sebade.relasiroom.database.modeldatabase.SeatTable
import com.sebade.relasiroom.model.HistoryFilm
import com.sebade.relasiroom.model.seat.SeatDetail
import com.sebade.relasiroom.network.UserItem
import com.sebade.relasiroom.repository.asDomainUser

class TicketRepository(val database: DatabaseMov) {
    var username: LiveData<UserItem> = Transformations.map(database.databaseDao.getUser()) {
        it.asDomainUser()
    }

    var historyList: LiveData<List<HistoryFilm>> =
        Transformations.map(database.databaseDao.getHistory()) {
            it.asDomainHistory()
        }

    suspend fun deleteAll(){
        database.databaseDao.deleteAllHistory()
    }

    suspend fun refresh(list: List<HistoryFilm>) {
        var userId = 0
        var seatPrimary = 0
        val newList = mutableListOf<HistoryFilmTable>()
        list.forEach {
            with(it) {
                var seat = mutableListOf<SeatTable>()
                it.seatDetail?.forEach {
                    seat.add(SeatTable(
                        seatPrimary,
                        userId,
                        it.isBooked,
                        it.row,
                        it.seatNumber
                    ))
                    seatPrimary++
                }
                database.databaseDao.insertSeat(seat)
                newList.add(
                    HistoryFilmTable(
                        director,
                        genre,
                        rating,
                        title!!,
                        judul,
                        poster,
                        desc,
                        directors,
                        userId
                    )
                )
                userId++
            }
        }
        database.databaseDao.insertHistory(newList)
    }


}

private fun List<HistoryFilmWithSeat>.asDomainHistory(): List<HistoryFilm> {
    return map {
        with(it) {
            with(historyFilmTable) {
                HistoryFilm(
                    director, genre, rating, title, judul, poster, desc, directors,it.seatTable.asDomainSeat()
                )
            }
        }
    }
}

private fun MutableList<SeatTable>.asDomainSeat(): MutableList<SeatDetail> {
    return map{
        with(it) {
            SeatDetail(
                isBooked, row, seatNumber
            )
        }
    }  as MutableList<SeatDetail>
}



