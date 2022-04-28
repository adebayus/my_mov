package com.sebade.relasiroom.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sebade.relasiroom.database.DatabaseMov
import com.sebade.relasiroom.database.FilmWithPlayer
import com.sebade.relasiroom.database.modeldatabase.ActorTable
import com.sebade.relasiroom.database.modeldatabase.FilmActorCrossRef
import com.sebade.relasiroom.database.modeldatabase.FilmTable
import com.sebade.relasiroom.database.modeldatabase.UserTable
import com.sebade.relasiroom.network.FilmItem
import com.sebade.relasiroom.network.PlayItem
import com.sebade.relasiroom.network.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmActorRepository(private val database: DatabaseMov) {

    val film: LiveData<List<FilmItem>> = Transformations.map(database.databaseDao.getFilm()) {

        it.asDomainModel()
    }
    val user: LiveData<UserItem> = Transformations.map(database.databaseDao.getUser()) {
        it.asDomainUser()
    }

//    private fun getFilm(){

//        database.databaseDao.getFilm()

    suspend fun refreshVideos(responseFilm: List<FilmItem?>) {
        var filmList: MutableList<FilmTable> = mutableListOf()
        var actorList: MutableList<ActorTable> = mutableListOf()
        var actorfilmRefList: MutableList<FilmActorCrossRef> = mutableListOf()

        withContext(Dispatchers.IO) {
            responseFilm!!.forEach { fItem ->
                with(fItem!!) {
                    val film = FilmTable(
                        director ?: directors,
                        genre,
                        rating,
                        title!!,
                        judul,
                        poster,
                        desc,
                        directors
                    )
                    filmList.add(film)

                    play!!.forEach {
                        val actor = ActorTable(it!!.nama.toString(), it.url)
                        actorList.add(actor)
                        actorfilmRefList.add(FilmActorCrossRef(title, it.nama.toString()))
                    }
                }
            }
        }
        database.databaseDao.insertFilm(filmList)
        database.databaseDao.insertActor(actorList)
        database.databaseDao.insertRefFilmActor(actorfilmRefList)

    }

    /*private fun insertToTable(responseFilm: List<FilmItem?>) {
        *//*var filmList: MutableList<FilmTable> = mutableListOf()
        var actorList: MutableList<ActorTable> = mutableListOf()
        var actorfilmRefList: MutableList<FilmActorCrossRef> = mutableListOf()

        responseFilm!!.forEach { fItem ->
            with(fItem!!) {
                val film = FilmTable(
                    director ?: directors,
                    genre,
                    rating,
                    title!!,
                    judul,
                    poster,
                    desc,
                    directors
                )
                filmList.add(film)

                play!!.forEach {
                    val actor = ActorTable(it!!.nama.toString(), it.url)
                    actorList.add(actor)
                    actorfilmRefList.add(FilmActorCrossRef(title, it.nama.toString()))
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            database.databaseDao.insertFilm(filmList)
            database.databaseDao.insertActor(actorList)
            database.databaseDao.insertRefFilmActor(actorfilmRefList)

//            withContext(Dispatchers.Main){
//                Log.d("TAG", "insertToTable: ${database.databaseDao.getFilm()} ")
//            }
        }*//*

    }*/


}

fun UserTable.asDomainUser(): UserItem {
    return UserItem(password, nama, saldo, email, url, username)
}

fun List<FilmWithPlayer>.asDomainModel(): List<FilmItem> {
    return map {
        Log.d("TAG", " asdasds: $it ")
        FilmItem(
            it.actor.asDomainActorModel(),
            it.film.director,
            it.film.genre,
            it.film.rating,
            it.film.title,
            it.film.judul,
            it.film.poster,
            it.film.desc,
            it.film.directors,
        )
    }
}

fun List<ActorTable>.asDomainActorModel(): List<PlayItem> {
    return map {
        PlayItem(
            it.nama,
            it.url
        )
    }
}
