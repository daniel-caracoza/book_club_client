package com.example.bookclub.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class User (
    @PrimaryKey
    val userId: Int,
    val username: String
)

@Entity(primaryKeys = ["userId", "bookId"])
data class UsersBooks (
    val userId: Int,
    val bookId: Double
    )


data class userWithBooks(
    @Embedded
    val user: User,

    @Relation(
        parentColumn = "userId",
        entityColumn = "bookId",
        associateBy = Junction(UsersBooks::class)
    )
    val books: List<DatabaseBook>
)

