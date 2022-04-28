package com.sebade.relasiroom.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sebade.relasiroom.database.modeldatabase.*

@Dao
interface DatabaseDao {

    @Transaction
    @Query("SELECT * FROM film_table")
    fun getFilm(): LiveData<List<FilmWithPlayer>>

    @Transaction
    @Query("SELECT * FROM film_table where title = :key")
    fun getFilmByKey(key: String): LiveData<FilmWithPlayer>

    @Transaction
    @Query("SELECT * FROM film_table where title = :key")
    suspend fun getFilmByKeyNonLiveData(key: String): FilmWithPlayer

    @Transaction
    @Query("SELECT * FROM actor_table ")
    suspend fun getActor(): List<PlayerWithFilm>

    @Query("SELECT * FROM user_table limit 1")
    fun getUser(): LiveData<UserTable>

    @Query("SELECT * FROM user_table")
    fun getUserr(): LiveData<List<UserTable>>

    @Query("SELECT * FROM filmactorcrossref_table")
    suspend fun getRef(): List<FilmActorCrossRef>

    @Query("SELECT * FROM history_order_table Limit 1")
    suspend fun getRefHistory(): UserFilmCrossRef

//

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: List<FilmTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor: List<ActorTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRefFilmActor(ref: List<FilmActorCrossRef>)


//


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history : MutableList<HistoryFilmTable>)

    @Query("SELECT * FROM history_film_table")
    fun getHistory() : LiveData<List<HistoryFilmWithSeat>>

    @Query("SELECT * FROM history_film_table")
    fun getHistoryNon() : MutableList<HistoryFilmWithSeat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeat(seat : MutableList<SeatTable>)

    @Query("DELETE FROM history_film_table")
    suspend fun deleteAllHistory()


    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRefUserFilm(ref: List<UserFilmCrossRef>)

    @Query("SELECT * FROM history_order_table")
    fun getHistory() : LiveData<List<UserFilmCrossRef>>*/

}