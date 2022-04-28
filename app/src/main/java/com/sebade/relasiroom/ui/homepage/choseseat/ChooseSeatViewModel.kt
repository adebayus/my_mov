package com.sebade.relasiroom.ui.homepage.choseseat

import android.app.Application
import androidx.lifecycle.*
import com.sebade.relasiroom.model.HeaderBeliTiket
import com.sebade.relasiroom.model.seat.SeatDetail
import com.sebade.relasiroom.model.seat.AllSeat
import com.sebade.relasiroom.model.seat.SeatGroup
import com.sebade.relasiroom.network.FilmItem

class ChooseSeatViewModel(app: Application) : AndroidViewModel(app) {

    val _rowA = MutableLiveData<SeatGroup>(SeatData.generateData("A").SeatGroupA)
    val _rowB = MutableLiveData<SeatGroup>(SeatData.generateData("B").SeatGroupB)
    val _rowC = MutableLiveData<SeatGroup>(SeatData.generateData("C").SeatGroupC)
    val _rowD = MutableLiveData<SeatGroup>(SeatData.generateData("D").SeatGroupD)

    val rowA: LiveData<SeatGroup> = _rowA
    val rowB: LiveData<SeatGroup> = _rowB
    val rowC: LiveData<SeatGroup> = _rowC
    val rowD: LiveData<SeatGroup> = _rowD

    private val arguments = MutableLiveData<FilmItem>()
    val headerBeli: LiveData<HeaderBeliTiket> = Transformations.switchMap(arguments) {
        MutableLiveData<HeaderBeliTiket>(HeaderBeliTiket("Pilih Bangku", it.title.toString()))
    }

    val listBuyTiket = mutableListOf<SeatDetail>()
    private val _listBuy = MutableLiveData<MutableList<SeatDetail>>()
    val listBuySize: LiveData<Int> =  Transformations.switchMap(_listBuy){
        MutableLiveData<Int>(listBuyTiket.size)
    }

    fun setArguments(key: FilmItem?) {
        arguments.value = key
    }


    fun buyTiket(seat: SeatDetail) {
        if (seat.isBooked != 1) {
            changeData(seat)
        }

        /*if (seat.isBooked == -1) {
            listBuyTiket.add(seat)
            changeData(seat)
        } else if (seat.isBooked == 0) {
            listBuyTiket.remove(seat)
            changeData(seat)
        }*/
//        _listBuy.value = listBuyTiket
        /*Log.d("TAG", "buyTiket: ${listBuyTiket} ")*/
    }

    private fun changeData(seat: SeatDetail) {
        when (seat.row) {
            "A" -> {
                seatNumber(_rowA, seat)
            }
            "B" -> seatNumber(_rowB, seat)
            "C" -> seatNumber(_rowC, seat)
            "D" -> seatNumber(_rowD, seat)
        }
    }

    private fun seatNumber(_row: MutableLiveData<SeatGroup>, seat: SeatDetail) {
        var clone = _row.value
        when (seat.seatNumber) {
            1 -> {
                if (_row.value?.seatOne?.isBooked == -1) {
                    listBuyTiket.add(seat)
                    clone?.seatOne?.isBooked = 0
                    _row.value = clone
                } else if (_row.value?.seatOne?.isBooked == 0) {
                    listBuyTiket.remove(seat)
                    clone?.seatOne?.isBooked = -1
                    _row.value = clone
                }
            }
            2 -> {
                if (_row.value?.seatTwo?.isBooked == -1) {
                    listBuyTiket.add(seat)
                    clone?.seatTwo?.isBooked = 0
                    _row.value = clone
                } else if (_row.value?.seatTwo?.isBooked == 0) {
                    listBuyTiket.remove(seat)
                    clone?.seatTwo?.isBooked = -1
                    _row.value = clone
                }
            }
            3 -> {
                if (_row.value?.seatThree?.isBooked == -1) {
                    listBuyTiket.add(seat)
                    clone?.seatThree?.isBooked = 0
                    _row.value = clone
                } else if (_row.value?.seatThree?.isBooked == 0) {
                    listBuyTiket.remove(seat)
                    clone?.seatThree?.isBooked = -1
                    _row.value = clone
                }
            }
            4 -> {
                if (_row.value?.seatFour?.isBooked == -1) {
                    listBuyTiket.add(seat)
                    clone?.seatFour?.isBooked = 0
                    _row.value = clone
                } else if (_row.value?.seatFour?.isBooked == 0) {
                    listBuyTiket.remove(seat)
                    clone?.seatFour?.isBooked = -1
                    _row.value = clone
                }
            }
        }
        _listBuy.value = listBuyTiket
    }

    val _navigated = MutableLiveData<Boolean>()


    fun navigatedToCheckout(){
        _navigated.value = true
    }

    fun navigatedToCheckoutDone(){
        _navigated.value = false
    }

    fun resetListBuy() {
        listBuyTiket.clear()
        _rowA.value = SeatData.generateData("A").SeatGroupA
        _rowB.value = SeatData.generateData("B").SeatGroupB
        _rowC.value = SeatData.generateData("C").SeatGroupC
        _rowD.value = SeatData.generateData("D").SeatGroupD
    }



}

class SeatData {
    companion object {
//        fun data(row String): AllSeat {
//            generateData()
//        }

        fun generateData(row: String): AllSeat {
            val group: SeatGroup by lazy {
                generate(row)
            }
            return AllSeat(group, group, group, group)
        }

        private fun generate(row: String): SeatGroup {
            return SeatGroup(
                SeatDetail(1, row, 1),
                SeatDetail(-1, row, 2),
                SeatDetail(-1, row, 3),
                SeatDetail(1, row, 4)
            )
        }
    }

}


class ChooseSeatViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChooseSeatViewModel::class.java)) {
            return ChooseSeatViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }

}


