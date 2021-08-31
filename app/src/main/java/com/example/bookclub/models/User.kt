package com.example.bookclub.models

import androidx.room.*

@Entity
data class User (
    @PrimaryKey
    val userId: String,
    val username: String
)

@Entity(primaryKeys = ["userId", "bookId"])
data class UsersBooks (
    val userId: String,
    val bookId: Double
    )


data class UserWithBooks(
    @Embedded
    val user: User,

    @Relation(
        parentColumn = "userId",
        entityColumn = "bookId",
        associateBy = Junction(UsersBooks::class)
    )
    val books: List<DatabaseBook>
)


