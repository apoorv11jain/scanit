package com.ajindustries.scanit

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [BookEntity::class, RollEntity::class ], version =2)
abstract class BookDatabase: RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun rollDao() : RollDao
}