package com.example.bookclub.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bookclub.models.SearchItem
import com.example.bookclub.models.UserWithSearchItems

@Dao
interface SearchItemDao {

    @Insert
    fun insertSearchItem(searchItem: SearchItem)

    @Delete
    fun deleteSearchItem(searchItem: SearchItem)

    @Transaction
    @Query("SELECT * FROM User")
    fun userSearchItems(): UserWithSearchItems
}