package com.example.bookclub.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookclub.models.DatabaseBook

@Database(entities = [DatabaseBook::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract val databaseBookDao: DatabaseBookDao

}