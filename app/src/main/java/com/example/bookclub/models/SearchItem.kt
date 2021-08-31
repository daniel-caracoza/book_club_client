package com.example.bookclub.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class SearchItem (

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userCreatorId: String,
    val searchTerm: String
    )

data class UserWithSearchItems(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )
    val userSearchItems: List<SearchItem>
)