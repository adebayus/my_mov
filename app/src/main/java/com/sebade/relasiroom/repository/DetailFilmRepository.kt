package com.sebade.relasiroom.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sebade.relasiroom.database.DatabaseMov
import com.sebade.relasiroom.database.FilmWithPlayer
import com.sebade.relasiroom.network.FilmItem
import com.sebade.relasiroom.network.PlayItem

class DetailFilmRepository(private val database: DatabaseMov) {
    fun getFilmByKey(key: String): LiveData<FilmItem> {
        Log.d("TAG", "getFilmByKey: tets")
        return Transformations.map(database.databaseDao.getFilmByKey(key)) {
            it.asDomainModelSingleFilm()
        }
    }

}

fun FilmWithPlayer.asDomainModelSingleFilm(): FilmItem {
    return FilmItem(
        actor.asDomainActorModel(),
        film.director ?: film.directors,
        film.genre,
        film.rating,
        film.title,
        film.judul,
        film.poster,
        film.desc,
        film.directors
    )
}