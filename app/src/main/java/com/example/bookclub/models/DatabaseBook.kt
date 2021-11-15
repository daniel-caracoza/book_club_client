package com.example.bookclub.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.UserBooksForClubCreationQuery
import com.example.UserBooksQuery
import java.io.Serializable

@Entity(tableName = "books")
data class DatabaseBook (
    @PrimaryKey
    val bookId: Long,

    val bookTitle: String,

    val image: String,

    val author: String,

    var currentPage: Int? = 0): Serializable

fun List<UserBooksQuery.UserBook>.mapToDatabaseBook(): List<DatabaseBook> {
    return map {
        DatabaseBook(
            it.id.toLong(),
            it.bookTitle,
            it.image,
            it.author,
            it.currentPage
        )
    }
}

fun List<UserBooksForClubCreationQuery.UserBook>.mapToClubDatabaseBook(): List<DatabaseBook> {
    return map {
        DatabaseBook(
            it.id.toLong(),
            it.bookTitle,
            it.image,
            it.author
        )
    }
}


