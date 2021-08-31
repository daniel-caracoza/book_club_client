package com.example.bookclub.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookclub.models.SearchItem
import com.example.bookclub.models.User

@Database(entities = [User::class, SearchItem::class], version = 3)
abstract class Database: RoomDatabase() {

    abstract val userDao: UserDao

    abstract val searchItemDao: SearchItemDao
}