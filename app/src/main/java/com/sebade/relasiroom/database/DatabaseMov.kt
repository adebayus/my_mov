package com.sebade.relasiroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sebade.relasiroom.database.modeldatabase.*

@Database(
    entities = [UserTable::class, FilmTable::class, ActorTable::class, FilmActorCrossRef::class, UserFilmCrossRef::class, HistoryFilmTable::class, SeatTable::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseMov : RoomDatabase() {

    abstract val databaseDao: DatabaseDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: DatabaseMov

        fun getDatabase(context: Context): com.sebade.relasiroom.database.DatabaseMov {
            synchronized(DatabaseMov::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseMov::class.java,
                        "user"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }

    }


}