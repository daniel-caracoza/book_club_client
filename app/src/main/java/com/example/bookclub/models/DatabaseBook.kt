package com.example.bookclub.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.UserBooksQuery

@Entity(tableName = "books")
data class DatabaseBook (
    @PrimaryKey
    val bookId: Double,

    val bookTitle: String,

    val image: String,

    val author: String,

    var currentPage: Int = 0)

fun List<UserBooksQuery.UserBook>.mapToDatabaseBook(): List<DatabaseBook> {
    return map {
        DatabaseBook(
            it.id,
            it.bookTitle,
            it.image,
            it.author,
            it.currentPage
        )
    }
}