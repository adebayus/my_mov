package com.sebade.relasiroom.database.modeldatabase

import androidx.room.Entity

@Entity(primaryKeys = ["username", "title"], tableName = "history_order_table")
class UserFilmCrossRef(
    val username: String,
    val title: String,
)
