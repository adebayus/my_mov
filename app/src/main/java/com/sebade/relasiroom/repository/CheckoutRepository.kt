package com.sebade.relasiroom.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sebade.relasiroom.database.DatabaseMov
import com.sebade.relasiroom.database.modeldatabase.UserTable
import com.sebade.relasiroom.network.FilmItem
import com.sebade.relasiroom.network.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckoutRepository(private val database : DatabaseMov) {
    val user : LiveData<UserItem> = Transformations.map(database.databaseDao.getUser()){ userTable ->
        userTable.asDomainUser()
    }

     suspend fun getFilmByKey(title : String) : FilmItem {
         lateinit var film : FilmItem
         withContext(Dispatchers.IO) {
             film =
                 database.databaseDao.getFilmByKeyNonLiveData(title).asDomainModelSingleFilm()
         }
        return film
    }

    suspend fun user() : LiveData<UserItem> {

        return Transformations.map(database.databaseDao.getUser()){ userTable ->
            userTable.asDomainUser()
        }
    }

}

private fun List<UserTable>.asDomainListUser() : List<UserItem> {
    return map {
        with(it) {
            UserItem(password, nama, saldo, email, url, username)
        }
    }
}
