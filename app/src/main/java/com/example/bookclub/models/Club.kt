package com.example.bookclub.models

import com.example.GetClubsQuery
import java.io.Serializable

data class Club(
    val id: String,
    val clubName: String,
    val clubImage: String,
    val clubBookTitle: String,
    val clubBookAuthor: String
): Serializable

fun List<GetClubsQuery.GetClub>.mapToDomainClub(): List<Club> {
    return map {
        Club(
            it.id,
            it.clubName,
            it.clubImage,
            it.clubBookTitle,
            it.clubBookAuthor
        )
    }
}