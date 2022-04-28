package com.sebade.relasiroom.ui.homepage.detailfilm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.sebade.relasiroom.database.DatabaseMov
import com.sebade.relasiroom.network.FilmItem
import com.sebade.relasiroom.repository.DetailFilmRepository

class DetailFilmModel(app: Application) : AndroidViewModel(app) {

    private val database = DatabaseMov.getDatabase(app)
    private val repository = DetailFilmRepository(database)

    private var arguments = MutableLiveData<String>()

    private var _navigatedPilihKursi = MutableLiveData<Boolean>()
    var navigatedPilihKursi : LiveData<Boolean> = _navigatedPilihKursi

    private var _onBackIconPressed = MutableLiveData<Boolean>()
    val onBackIconPressed: LiveData<Boolean> = _onBackIconPressed

    fun pilihBangku(){
        _navigatedPilihKursi.value = true
    }

    fun navigatedPilihKursiDone(){
        _navigatedPilihKursi.value = false
    }

    fun setArguments(key: String) {
        arguments.value = key
    }


    var film: LiveData<FilmItem> = Transformations.switchMap(arguments) {
        repository.getFilmByKey(arguments.value!!)

    }

    init {
        /*Log.d("TAG", " listSeat: ${listSeat.value} ")*/
        Log.d("TAG", "test argument test $arguments : ")
        if (!arguments.value.isNullOrBlank()) {
            film = repository.getFilmByKey(arguments.value!!)
        }
        Log.d("TAG", "test argument: $arguments")
    }


    fun onBackPressed() {
        _onBackIconPressed.value = true
    }

    fun onBackPressedDone() {
        _onBackIconPressed.value = false
    }


    override fun onCleared() {
        super.onCleared()
        Log.d("TAG", "onCleared: test ")
    }

    companion object {

    }

}


class DetailFilmFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DetailFilmModel::class.java)) {
            return DetailFilmModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}