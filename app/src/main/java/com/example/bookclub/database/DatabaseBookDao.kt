package com.example.bookclub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.bookclub.models.DatabaseBook
import com.example.bookclub.models.UsersBooks

@Dao
interface DatabaseBookDao {

    @Insert
    fun insertBook(books: DatabaseBook)

//    @Transaction
//    @Query("SELECT * FROM USER WHERE userId=:userId")
//    fun getUserBooks(userId: Int): LiveData<List<UsersBooks>>
}